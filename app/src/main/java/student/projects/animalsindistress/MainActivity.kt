package student.projects.animalsindistress

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.firestore.ktx.firestore
import android.Manifest
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import java.time.*
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var backButton: ImageButton
    private lateinit var toolbarLogo: ImageView
    private lateinit var toolbarTitle: TextView
    private lateinit var toolbarUserEmail: TextView
    private lateinit var navController: androidx.navigation.NavController
    private val recentPages = mutableListOf<Pair<Int, String>>() // Store (id, title) pairs
    private val maxRecentPages = 5
    private var authListener: FirebaseAuth.AuthStateListener? = null
    
    // Top-level destinations that don't show back button
    private val topLevelDestinations = setOf(
        R.id.homeFragment,
        R.id.aboutFragment,
        R.id.galleryFragment,
        R.id.donateFragment,
        R.id.moreFragment
    )
    
    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Initialize Firebase (safe to call multiple times)
        try {
            if (FirebaseApp.getApps(this).isEmpty()) {
                FirebaseApp.initializeApp(this)
            }
        } catch (_: Exception) { }
        setContentView(R.layout.activity_main)

        // Seed admin account on startup (idempotent)
        seedAdminOnStartup()

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Setup drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)
        backButton = findViewById(R.id.back_button)
        toolbarLogo = findViewById(R.id.toolbar_logo)
        toolbarTitle = findViewById(R.id.toolbar_title)
        toolbarUserEmail = findViewById(R.id.toolbar_user_email)

        // Menu button on RIGHT opens drawer from LEFT (START)
        val menuButton = findViewById<ImageButton>(R.id.menu_button)
        menuButton.setOnClickListener {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        // Get NavController from NavHostFragment
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        
        // Back button functionality
        backButton.setOnClickListener {
            navController.navigateUp()
        }

        // Track navigation changes for recent pages and back button visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val destinationId = destination.id
            val destinationLabel = destination.label?.toString() ?: "Unknown"
            
            // Show/hide back button and update toolbar based on destination
            if (destinationId in topLevelDestinations) {
                backButton.visibility = View.GONE
                toolbarLogo.visibility = View.VISIBLE
                toolbarTitle.visibility = View.GONE
            } else {
                backButton.visibility = View.VISIBLE
                toolbarLogo.visibility = View.GONE
                toolbarTitle.visibility = View.VISIBLE
                toolbarTitle.text = formatLabel(destinationLabel)
            }
            updateToolbarUserEmail()
            
            Log.d(TAG, "Navigation changed to: $destinationLabel (ID: $destinationId)")
            
            // Don't add to recent if it's already the most recent
            if (recentPages.firstOrNull()?.first != destinationId) {
                // Remove if already in list
                recentPages.removeAll { it.first == destinationId }
                // Add to front
                recentPages.add(0, destinationId to destinationLabel)
                // Keep only last 5
                if (recentPages.size > maxRecentPages) {
                    recentPages.removeAt(recentPages.size - 1)
                }
                // Update drawer menu
                updateDrawerMenu()
                Log.d(TAG, "Recent pages updated: ${recentPages.map { it.second }}")
            }
        }

        // Drawer navigation
        navigationView.setNavigationItemSelectedListener { menuItem ->
            val currentDestinationId = navController.currentDestination?.id
            drawerLayout.closeDrawer(GravityCompat.START)
            
            // Only navigate if we're not already on that destination
            if (currentDestinationId != menuItem.itemId) {
                try {
                    Log.d(TAG, "Drawer navigation to: ${menuItem.itemId}")
                    navController.navigate(menuItem.itemId)
                } catch (e: Exception) {
                    Log.e(TAG, "Drawer navigation error", e)
                }
            } else {
                Log.d(TAG, "Already on destination: ${menuItem.itemId}, not navigating")
            }
            true
        }

        // Bottom navigation - use setupWithNavController for automatic handling
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)
        
        // Initialize drawer menu
        updateDrawerMenu()
        updateToolbarUserEmail()
        scheduleUserReminders()
    }

    private fun seedAdminOnStartup() {
        // Do not disrupt an active user session
        if (FirebaseAuth.getInstance().currentUser != null) return
        val email = getString(R.string.dev_admin_email)
        val password = getString(R.string.dev_admin_password)
        if (email.isBlank() || password.isBlank()) return

        val auth = FirebaseAuth.getInstance()
        auth.fetchSignInMethodsForEmail(email)
            .addOnSuccessListener { methods ->
                if (methods.signInMethods.isNullOrEmpty()) {
                    // Create admin auth user
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener { result ->
                            val uid = result.user?.uid ?: return@addOnSuccessListener
                            upsertAdminUserDoc(uid, email) {
                                // keep user signed out after seeding
                                auth.signOut()
                            }
                        }
                        .addOnFailureListener { _ ->
                            // If collision or other error, try sign in then ensure doc
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener { res ->
                                    val uid = res.user?.uid
                                    if (uid != null) {
                                        upsertAdminUserDoc(uid, email) { auth.signOut() }
                                    } else {
                                        auth.signOut()
                                    }
                                }
                                .addOnFailureListener { _ -> }
                        }
                } else {
                    // Exists: sign in to get uid, ensure doc, sign out
                    auth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener { res ->
                            val uid = res.user?.uid
                            if (uid != null) {
                                upsertAdminUserDoc(uid, email) { auth.signOut() }
                            } else {
                                auth.signOut()
                            }
                        }
                        .addOnFailureListener { _ -> }
                }
            }
            .addOnFailureListener { _ -> }
    }

    private fun upsertAdminUserDoc(uid: String, email: String, onDone: () -> Unit) {
        val db = Firebase.firestore
        val data = hashMapOf(
            "email" to email,
            "role" to "admin",
            "createdAt" to Timestamp.now()
        )
        db.collection("users").document(uid)
            .set(data, SetOptions.merge())
            .addOnSuccessListener { onDone() }
            .addOnFailureListener { onDone() }
    }

    private fun scheduleUserReminders() {
        val user = FirebaseAuth.getInstance().currentUser ?: return
        if (Build.VERSION.SDK_INT >= 33 && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 2001)
        }

        val db = Firebase.firestore
        db.collectionGroup("reminders")
            .whereEqualTo("uid", user.uid)
            .get()
            .addOnSuccessListener { snaps ->
                snaps.documents.forEach { reminderDoc ->
                    val eventRef = reminderDoc.reference.parent.parent ?: return@forEach
                    eventRef.get().addOnSuccessListener { eventSnap ->
                        val id = eventRef.id
                        val title = eventSnap.getString("title") ?: return@addOnSuccessListener
                        val description = eventSnap.getString("description") ?: ""
                        val dateStr = eventSnap.getString("date") ?: return@addOnSuccessListener
                        val startStr = eventSnap.getString("startTime") ?: return@addOnSuccessListener
                        try {
                            val date = LocalDate.parse(dateStr)
                            val startTime = LocalTime.parse(startStr, DateTimeFormatter.ofPattern("HH:mm"))
                            scheduleExactReminder(id, title, description, date, startTime, user.uid)
                        } catch (_: Exception) { }
                    }
                }
            }
    }

    private fun scheduleExactReminder(eventId: String, title: String, description: String, date: LocalDate, startTime: LocalTime, uid: String) {
        val triggerDateTime = date.atTime(startTime).minusHours(24)
        val now = LocalDateTime.now()
        val alarmManager = getSystemService(ALARM_SERVICE) as? AlarmManager ?: return
        val intent = Intent(this, NotificationReceiver::class.java).apply {
            putExtra("event_title", title)
            val timeFormatter = DateTimeFormatter.ofPattern("h:mm a")
            val dateFormatter = DateTimeFormatter.ofPattern("EEE, MMM d")
            val timeText = "at ${startTime.format(timeFormatter)} on ${date.format(dateFormatter)}"
            putExtra("event_time_text", timeText)
            putExtra("event_description", description)
            putExtra("event_id", eventId)
            putExtra("uid", uid)
        }
        val requestCode = (eventId + ":" + uid).hashCode()
        val pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_IMMUTABLE)
        val triggerAtMillis = if (triggerDateTime.isBefore(now)) System.currentTimeMillis() + 5_000 else
            triggerDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
            } catch (_: SecurityException) { }
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
        }
    }
    
    private fun updateDrawerMenu() {
        Log.d(TAG, "updateDrawerMenu called. Recent pages count: ${recentPages.size}")
        
        val menu = navigationView.menu
        menu.clear()
        
        // Recent Pages Section (if any)
        if (recentPages.isNotEmpty()) {
            Log.d(TAG, "Adding recent pages section with ${recentPages.size} items")
            val recentGroup = menu.addSubMenu(0, 0, 0, "Recent Pages")
            recentPages.forEach { (id, label) ->
                val formattedLabel = formatLabel(label)
                Log.d(TAG, "Adding recent page: $formattedLabel (ID: $id)")
                val menuItem = recentGroup.add(0, id, 0, formattedLabel)
                // Add icon based on destination
                menuItem.icon = getIconForDestination(id)
            }
        } else {
            Log.d(TAG, "No recent pages to display")
        }
        
        // Quick Links Section
        val quickLinksGroup = menu.addSubMenu(0, 0, 1, "Quick Links")
        quickLinksGroup.add(1, R.id.volunteerFragment, 0, "Volunteer").setIcon(R.drawable.ic_person_24)
        quickLinksGroup.add(1, R.id.eventCalendarFragment, 0, "Events").setIcon(R.drawable.ic_person_24)
        quickLinksGroup.add(1, R.id.newsListFragment, 0, "News").setIcon(R.drawable.ic_article_24)
        quickLinksGroup.add(1, R.id.storiesFeedFragment, 0, "Success Stories").setIcon(R.drawable.ic_favorite_24)
        quickLinksGroup.add(1, R.id.teamFragment, 0, "Our Team").setIcon(R.drawable.ic_group_24)
        quickLinksGroup.add(1, R.id.shopFragment, 0, "Shop").setIcon(R.drawable.ic_shopping_cart_24)
        quickLinksGroup.add(1, R.id.contactFragment, 0, "Contact Us").setIcon(R.drawable.ic_contact_mail_24)
        
        // Programs Section
        val programsGroup = menu.addSubMenu(0, 0, 2, "Programs")
        programsGroup.add(2, R.id.servicesFragment, 0, "Services").setIcon(R.drawable.ic_pets_24)
        programsGroup.add(2, R.id.equineOutreachFragment, 0, "Equine Outreach").setIcon(R.drawable.ic_pets_24)
        
        Log.d(TAG, "Drawer menu updated successfully")
    }
    
    private fun getIconForDestination(id: Int): android.graphics.drawable.Drawable? {
        val iconRes = when (id) {
            R.id.homeFragment -> R.drawable.ic_home_24
            R.id.aboutFragment -> R.drawable.ic_info_24
            R.id.galleryFragment -> R.drawable.ic_collections_24
            R.id.donateFragment -> R.drawable.ic_favorite_24
            R.id.moreFragment -> R.drawable.ic_more_horiz_24
            R.id.contactFragment -> R.drawable.ic_contact_mail_24
            R.id.newsListFragment -> R.drawable.ic_article_24
            R.id.storiesFeedFragment -> R.drawable.ic_favorite_24
            R.id.teamFragment -> R.drawable.ic_group_24
            R.id.servicesFragment -> R.drawable.ic_pets_24
            R.id.programsFragment -> R.drawable.ic_category_24
            R.id.volunteerFragment -> R.drawable.ic_person_24
            R.id.shopFragment -> R.drawable.ic_shopping_cart_24
            R.id.partnersFragment -> R.drawable.ic_people_24
            R.id.equineOutreachFragment -> R.drawable.ic_pets_24
            else -> null
        }
        return iconRes?.let { getDrawable(it) }
    }
    
    private fun formatLabel(label: String): String {
        // Convert labels like "home" to "Home"
        return label.split("-", "_").joinToString(" ") { 
            it.replaceFirstChar { char -> char.uppercase() } 
        }
    }

    override fun onResume() {
        super.onResume()
        updateToolbarUserEmail()
        scheduleUserReminders()
    }

    override fun onStart() {
        super.onStart()
        if (authListener == null) {
            authListener = FirebaseAuth.AuthStateListener {
                updateToolbarUserEmail()
                scheduleUserReminders()
            }
        }
        FirebaseAuth.getInstance().addAuthStateListener(authListener!!)
    }

    override fun onStop() {
        super.onStop()
        authListener?.let { FirebaseAuth.getInstance().removeAuthStateListener(it) }
    }

    private fun updateToolbarUserEmail() {
        val email = FirebaseAuth.getInstance().currentUser?.email
        if (email.isNullOrBlank()) {
            toolbarUserEmail.visibility = View.GONE
            toolbarUserEmail.text = ""
        } else {
            toolbarUserEmail.visibility = View.VISIBLE
            toolbarUserEmail.text = email
        }
    }
}
package student.projects.animalsindistress

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private val recentPages = mutableListOf<Pair<Int, String>>() // Store (id, title) pairs
    private val maxRecentPages = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Setup toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Setup drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigation_view)

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
        val navController = navHostFragment.navController

        // Track navigation changes for recent pages
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val destinationId = destination.id
            val destinationLabel = destination.label?.toString() ?: "Unknown"
            
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
            }
        }

        // Drawer navigation
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.closeDrawer(GravityCompat.START)
            try {
                navController.navigate(menuItem.itemId)
            } catch (e: Exception) {
                // Ignore navigation errors for invalid destinations
            }
            true
        }

        // Bottom navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setOnItemSelectedListener { item ->
            try {
                navController.navigate(item.itemId) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    popUpTo(navController.graph.startDestinationId) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            } catch (e: Exception) {
                // Ignore navigation errors for invalid destinations
            }
            true
        }
        
        // Initialize drawer menu
        updateDrawerMenu()
    }
    
    private fun updateDrawerMenu() {
        val menu = navigationView.menu
        menu.clear()
        
        // Recent Pages Section (if any)
        if (recentPages.isNotEmpty()) {
            val recentGroup = menu.addSubMenu(0, 0, 0, "Recent Pages")
            recentPages.forEach { (id, label) ->
                val menuItem = recentGroup.add(0, id, 0, formatLabel(label))
                // Add icon based on destination
                menuItem.icon = getIconForDestination(id)
            }
        }
        
        // Quick Links Section
        val quickLinksGroup = menu.addSubMenu(0, 0, 1, "Quick Links")
        quickLinksGroup.add(1, R.id.volunteerFragment, 0, "Volunteer").setIcon(R.drawable.ic_person_24)
        quickLinksGroup.add(1, R.id.newsListFragment, 0, "News").setIcon(R.drawable.ic_article_24)
        quickLinksGroup.add(1, R.id.teamFragment, 0, "Our Team").setIcon(R.drawable.ic_group_24)
        quickLinksGroup.add(1, R.id.shopFragment, 0, "Shop").setIcon(R.drawable.ic_shopping_cart_24)
        quickLinksGroup.add(1, R.id.contactFragment, 0, "Contact Us").setIcon(R.drawable.ic_contact_mail_24)
        
        // Programs Section
        val programsGroup = menu.addSubMenu(0, 0, 2, "Programs")
        programsGroup.add(2, R.id.servicesFragment, 0, "Services").setIcon(R.drawable.ic_pets_24)
        programsGroup.add(2, R.id.equineOutreachFragment, 0, "Equine Outreach").setIcon(R.drawable.ic_pets_24)
        programsGroup.add(2, R.id.partnersFragment, 0, "Partners").setIcon(R.drawable.ic_people_24)
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
}
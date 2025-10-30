package student.projects.animalsindistress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import student.projects.animalsindistress.ui.theme.AnimalsInDistressTheme
import student.projects.animalsindistress.navigation.AppNavHost
import student.projects.animalsindistress.ui.components.BottomNavBar
import student.projects.animalsindistress.ui.components.MobileTopBar
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimalsInDistressTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route
                val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
                val scope = rememberCoroutineScope()
                
                // Track recent pages (last 5 unique pages visited)
                var recentPages by remember { mutableStateOf<List<String>>(emptyList()) }
                
                // Update recent pages when route changes
                LaunchedEffect(currentRoute) {
                    if (currentRoute != null) {
                        recentPages = (listOf(currentRoute) + recentPages)
                            .distinct()
                            .take(5)
                    }
                }
                
                // Determine if we should show bottom nav on this screen
                val showBottomNav = currentRoute in listOf("home", "about", "gallery", "donate", "more")
                
                // Get title based on current route
                val title = when (currentRoute) {
                    "home" -> "Animals In Distress"
                    "about" -> "About Us"
                    "gallery" -> "Gallery"
                    "donate" -> "Donate"
                    "more" -> "More"
                    "contact" -> "Contact Us"
                    "news" -> "News"
                    "team" -> "Our Team"
                    "services" -> "Services"
                    "programs" -> "Programs"
                    "volunteer" -> "Volunteer"
                    "shop" -> "Shop"
                    "dancing-donkey" -> "The Dancing Donkey"
                    "partners" -> "Partners"
                    "equine-outreach" -> "Equine Outreach"
                    else -> "Animals In Distress"
                }
                
                ModalNavigationDrawer(
                    drawerState = drawerState,
                    gesturesEnabled = true,
                    drawerContent = {
                        ModalDrawerSheet(
                            modifier = Modifier.fillMaxWidth(0.75f) // Only 75% of screen width
                        ) {
                            DrawerContent(
                                recentPages = recentPages,
                                onNavigate = { route ->
                                    scope.launch { drawerState.close() }
                                    navController.navigate(route)
                                }
                            )
                        }
                    }
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        topBar = {
                            MobileTopBar(
                                title = title,
                                navController = navController,
                                showBackButton = !showBottomNav,
                                onMenuClick = { scope.launch { drawerState.open() } }
                            )
                        },
                        bottomBar = {
                            if (showBottomNav) {
                                BottomNavBar(navController = navController)
                            }
                        }
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavHost(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

// Helper function to get route info (icon and title)
fun getRouteInfo(route: String): Pair<ImageVector, String> {
    return when (route) {
        "home" -> Icons.Default.Home to "Home"
        "about" -> Icons.Default.Info to "About Us"
        "gallery" -> Icons.Default.Image to "Gallery"
        "donate" -> Icons.Default.Favorite to "Donate"
        "more" -> Icons.Default.Menu to "More"
        "contact" -> Icons.Default.ContactMail to "Contact Us"
        "news" -> Icons.Default.Article to "News"
        "team" -> Icons.Default.Group to "Our Team"
        "services" -> Icons.Default.Pets to "Services"
        "programs" -> Icons.Default.Category to "Programs"
        "volunteer" -> Icons.Default.Person to "Volunteer"
        "shop" -> Icons.Default.ShoppingCart to "Shop"
        "dancing-donkey" -> Icons.Default.Restaurant to "Dancing Donkey"
        "partners" -> Icons.Default.People to "Partners"
        "equine-outreach" -> Icons.Default.Pets to "Equine Outreach"
        "faq" -> Icons.Default.Help to "FAQ"
        "privacy" -> Icons.Default.PrivacyTip to "Privacy Policy"
        "terms" -> Icons.Default.Description to "Terms"
        "sitemap" -> Icons.Default.Map to "Sitemap"
        "sponsorship" -> Icons.Default.Star to "Sponsorship"
        "education" -> Icons.Default.School to "Education"
        "wildlife" -> Icons.Default.Nature to "Wildlife Care"
        "legacy" -> Icons.Default.Favorite to "Legacy Giving"
        "corporate" -> Icons.Default.Business to "Corporate Support"
        "events" -> Icons.Default.Event to "Events"
        "careers" -> Icons.Default.Work to "Careers"
        "annual-reports" -> Icons.Default.Assessment to "Annual Reports"
        "impact" -> Icons.Default.TrendingUp to "Impact Stories"
        "merchandise" -> Icons.Default.ShoppingBag to "Merchandise"
        "memberships" -> Icons.Default.CardMembership to "Memberships"
        "heart-sole" -> Icons.Default.FavoriteBorder to "Heart & Sole"
        "golf-day" -> Icons.Default.Sports to "Golf Day"
        "special-events" -> Icons.Default.Celebration to "Special Events"
        else -> Icons.Default.Article to route.replace("-", " ").replaceFirstChar { it.uppercase() }
    }
}

@Composable
fun DrawerContent(
    recentPages: List<String>,
    onNavigate: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(Modifier.height(24.dp))
        
        // Logo at top of drawer
        Image(
            painter = painterResource(id = R.drawable.said_logo),
            contentDescription = "Animals In Distress Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(bottom = 16.dp)
        )
        
        HorizontalDivider(modifier = Modifier.padding(bottom = 16.dp))
        
        // Recent Pages Section
        if (recentPages.isNotEmpty()) {
            Text(
                "Recent Pages",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            recentPages.forEach { route ->
                val (icon, title) = getRouteInfo(route)
                DrawerItem(
                    icon = icon,
                    title = title,
                    onClick = { onNavigate(route) }
                )
            }
            
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        }
        
        Text(
            "Quick Links",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        // Important/Frequently accessed pages (NOT in bottom nav)
        DrawerItem(
            icon = Icons.Default.Person,
            title = "Volunteer",
            onClick = { onNavigate("volunteer") }
        )
        
        DrawerItem(
            icon = Icons.Default.Article,
            title = "News",
            onClick = { onNavigate("news") }
        )
        
        DrawerItem(
            icon = Icons.Default.Group,
            title = "Our Team",
            onClick = { onNavigate("team") }
        )
        
        DrawerItem(
            icon = Icons.Default.ShoppingCart,
            title = "Shop",
            onClick = { onNavigate("shop") }
        )
        
        DrawerItem(
            icon = Icons.Default.ContactMail,
            title = "Contact Us",
            onClick = { onNavigate("contact") }
        )
        
        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
        
        Text(
            "Programs",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        
        DrawerItem(
            icon = Icons.Default.Pets,
            title = "Services",
            onClick = { onNavigate("services") }
        )
        
        DrawerItem(
            icon = Icons.Default.Pets,
            title = "Equine Outreach",
            onClick = { onNavigate("equine-outreach") }
        )
        
        DrawerItem(
            icon = Icons.Default.People,
            title = "Partners",
            onClick = { onNavigate("partners") }
        )
    }
}

@Composable
private fun DrawerItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
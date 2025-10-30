package student.projects.animalsindistress.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.Primary
import student.projects.animalsindistress.ui.theme.PrimaryForeground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(onNavigate: (String) -> Unit) {
    val context = LocalContext.current
    var isMenuOpen by remember { mutableStateOf(false) }
    var activeDropdown by remember { mutableStateOf<String?>(null) }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Top Contact Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Primary)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contact Info
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
                        context.startActivity(intent)
                    }
                ) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = "Phone",
                        tint = PrimaryForeground,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "011 466 0261",
                        color = PrimaryForeground,
                        fontSize = 12.sp
                    )
                }
                
                Text("|", color = PrimaryForeground, fontSize = 12.sp)
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
                        context.startActivity(intent)
                    }
                ) {
                    Icon(
                        Icons.Default.Email,
                        contentDescription = "Email",
                        tint = PrimaryForeground,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        "animals@animalsindistress.org.za",
                        color = PrimaryForeground,
                        fontSize = 12.sp
                    )
                }
            }
            
            // Social Media Icons
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Default.Face, "Facebook", tint = PrimaryForeground, modifier = Modifier.size(16.dp))
                }
                IconButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(Icons.Default.AccountCircle, "Instagram", tint = PrimaryForeground, modifier = Modifier.size(16.dp))
                }
            }
        }
        
        // Main Navigation
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Logo
            Text(
                "SAID",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = Primary
                ),
                modifier = Modifier.clickable { onNavigate("home") }
            )
            
            // Desktop Navigation (hidden on mobile, would need proper responsive logic)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // About Us Dropdown
                DropdownMenuItem(
                    label = "About Us",
                    items = listOf(
                        "News" to "news",
                        "Meet The Team" to "team",
                        "Equine Outreach" to "equine-outreach",
                        "Partners" to "partners",
                        "Annual Report" to "annual-report"
                    ),
                    onNavigate = onNavigate,
                    isExpanded = activeDropdown == "about",
                    onToggle = { activeDropdown = if (activeDropdown == "about") null else "about" }
                )
                
                TextButton(onClick = { onNavigate("dancing-donkey") }) {
                    Text("The Dancing Donkey", fontSize = 14.sp)
                }
                
                // Support Us Dropdown
                DropdownMenuItem(
                    label = "Support Us",
                    items = listOf(
                        "Make A Donation" to "donate",
                        "Volunteer" to "volunteer",
                        "Shop" to "shop"
                    ),
                    onNavigate = onNavigate,
                    isExpanded = activeDropdown == "support",
                    onToggle = { activeDropdown = if (activeDropdown == "support") null else "support" }
                )
                
                TextButton(onClick = { onNavigate("gallery") }) {
                    Text("Gallery", fontSize = 14.sp)
                }
                
                TextButton(onClick = { onNavigate("contact") }) {
                    Text("Contact Us", fontSize = 14.sp)
                }
            }
            
            // Mobile Menu Icon
            IconButton(onClick = { isMenuOpen = !isMenuOpen }) {
                Icon(
                    if (isMenuOpen) Icons.Default.Close else Icons.Default.Menu,
                    contentDescription = "Menu"
                )
            }
        }
        
        // Mobile Menu
        if (isMenuOpen) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                MobileMenuItem("Home", onClick = { onNavigate("home"); isMenuOpen = false })
                MobileMenuItem("About Us", onClick = { onNavigate("about"); isMenuOpen = false })
                MobileMenuItem("News", onClick = { onNavigate("news"); isMenuOpen = false })
                MobileMenuItem("Team", onClick = { onNavigate("team"); isMenuOpen = false })
                MobileMenuItem("Partners", onClick = { onNavigate("partners"); isMenuOpen = false })
                MobileMenuItem("Dancing Donkey", onClick = { onNavigate("dancing-donkey"); isMenuOpen = false })
                MobileMenuItem("Donate", onClick = { onNavigate("donate"); isMenuOpen = false })
                MobileMenuItem("Volunteer", onClick = { onNavigate("volunteer"); isMenuOpen = false })
                MobileMenuItem("Shop", onClick = { onNavigate("shop"); isMenuOpen = false })
                MobileMenuItem("Gallery", onClick = { onNavigate("gallery"); isMenuOpen = false })
                MobileMenuItem("Contact", onClick = { onNavigate("contact"); isMenuOpen = false })
            }
        }
    }
}

@Composable
private fun DropdownMenuItem(
    label: String,
    items: List<Pair<String, String>>,
    onNavigate: (String) -> Unit,
    isExpanded: Boolean,
    onToggle: () -> Unit
) {
    Box {
        TextButton(onClick = onToggle) {
            Text(label, fontSize = 14.sp)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, modifier = Modifier.size(16.dp))
        }
        
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = onToggle
        ) {
            items.forEach { (itemLabel, route) ->
                DropdownMenuItem(
                    text = { Text(itemLabel, fontSize = 14.sp) },
                    onClick = {
                        onNavigate(route)
                        onToggle()
                    }
                )
            }
        }
    }
}

@Composable
private fun MobileMenuItem(label: String, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            label,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 16.sp
        )
    }
}



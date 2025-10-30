package student.projects.animalsindistress.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

@Composable
fun MoreScreen(onNavigate: (String) -> Unit) {
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header
        item {
            Text(
                "More Options",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Foreground,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        
        // Support Section
        item {
            SectionHeader("Support Us")
        }
        
        item {
            MenuCard {
                MenuItem(
                    icon = Icons.Default.Person,
                    title = "Volunteer",
                    subtitle = "Join our team",
                    onClick = { onNavigate("volunteer") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.ShoppingCart,
                    title = "Shop",
                    subtitle = "Browse our store",
                    onClick = { onNavigate("shop") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Celebration,
                    title = "The Dancing Donkey",
                    subtitle = "Farm party venue",
                    onClick = { onNavigate("dancing-donkey") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.ShoppingBag,
                    title = "Heart & Sole Store",
                    subtitle = "Step into purpose",
                    onClick = { onNavigate("heart-sole-store") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.GolfCourse,
                    title = "Golf Day 2025",
                    subtitle = "Annual fundraiser",
                    onClick = { onNavigate("golf-day-2025") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Favorite,
                    title = "1000 Heroes",
                    subtitle = "Monthly giving program",
                    onClick = { onNavigate("thousand-heroes") }
                )
            }
        }
        
        // Donation Programs Section
        item {
            SectionHeader("Ways to Give")
        }
        
        item {
            MenuCard {
                MenuItem(
                    icon = Icons.Default.School,
                    title = "MySchool",
                    subtitle = "MySchool My Village My Planet",
                    onClick = { onNavigate("myschool") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.CalendarMonth,
                    title = "Monthly Debit Order",
                    subtitle = "Regular giving",
                    onClick = { onNavigate("monthly-debit") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Payment,
                    title = "Payroll Giving",
                    subtitle = "Give through your salary",
                    onClick = { onNavigate("payroll-giving") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.HistoryEdu,
                    title = "Legacies & Bequests",
                    subtitle = "Leave a lasting impact",
                    onClick = { onNavigate("legacies") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Recycling,
                    title = "Pre-Loved Donations",
                    subtitle = "Donate items",
                    onClick = { onNavigate("pre-loved") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Receipt,
                    title = "Tax Certificate",
                    subtitle = "18A tax certificate info",
                    onClick = { onNavigate("tax-certificate") }
                )
            }
        }
        
        // About Section
        item {
            SectionHeader("About Us")
        }
        
        item {
            MenuCard {
                MenuItem(
                    icon = Icons.Default.Article,
                    title = "News",
                    subtitle = "Latest updates",
                    onClick = { onNavigate("news") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.AutoStories,
                    title = "Case Study",
                    subtitle = "Success stories",
                    onClick = { onNavigate("case-study") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Group,
                    title = "Our Team",
                    subtitle = "Meet the team",
                    onClick = { onNavigate("team") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.People,
                    title = "Partners",
                    subtitle = "Our supporters",
                    onClick = { onNavigate("partners") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Description,
                    title = "Annual Report",
                    subtitle = "View our reports",
                    onClick = { onNavigate("annual-report") }
                )
            }
        }
        
        // Programs Section
        item {
            SectionHeader("Programs")
        }
        
        item {
            MenuCard {
                MenuItem(
                    icon = Icons.Default.Pets,
                    title = "Services",
                    subtitle = "What we offer",
                    onClick = { onNavigate("services") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Diversity3,
                    title = "Programs",
                    subtitle = "Our initiatives",
                    onClick = { onNavigate("programs") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Pets,
                    title = "Equine Outreach",
                    subtitle = "Horse care program",
                    onClick = { onNavigate("equine-outreach") }
                )
            }
        }
        
        // Contact Section
        item {
            SectionHeader("Get In Touch")
        }
        
        item {
            MenuCard {
                MenuItem(
                    icon = Icons.Default.ContactMail,
                    title = "Contact Us",
                    subtitle = "Get in touch",
                    onClick = { onNavigate("contact") }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Phone,
                    title = "Call Us",
                    subtitle = "011 466 0261",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
                        context.startActivity(intent)
                    }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.Email,
                    title = "Email Us",
                    subtitle = "animals@animalsindistress.org.za",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
                        context.startActivity(intent)
                    }
                )
            }
        }
        
        // Social Media Section
        item {
            SectionHeader("Follow Us")
        }
        
        item {
            MenuCard {
                MenuItem(
                    icon = Icons.Default.Face,
                    title = "Facebook",
                    subtitle = "@tsfaid",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
                        context.startActivity(intent)
                    }
                )
                Divider()
                MenuItem(
                    icon = Icons.Default.PhotoCamera,
                    title = "Instagram",
                    subtitle = "@thesocietyforanimalsindistress",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
                        context.startActivity(intent)
                    }
                )
            }
        }
        
        // FAQ Link
        item {
            MenuCard {
                MenuItem(
                    icon = Icons.Default.Help,
                    title = "FAQ",
                    subtitle = "Frequently asked questions",
                    onClick = { onNavigate("faq") }
                )
            }
        }
        
        // Bottom spacing
        item {
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        ),
        color = MutedForeground,
        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
    )
}

@Composable
private fun MenuCard(content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            content()
        }
    }
}

@Composable
private fun MenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Medium
                ),
                color = Foreground
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MutedForeground,
                fontSize = 13.sp
            )
        }
        Icon(
            imageVector = Icons.Default.ChevronRight,
            contentDescription = null,
            tint = MutedForeground,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
private fun Divider() {
    HorizontalDivider(
        color = Border,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
}

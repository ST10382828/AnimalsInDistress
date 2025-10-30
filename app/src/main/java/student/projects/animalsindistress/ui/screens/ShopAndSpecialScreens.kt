package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

// Shop Screen
@Composable
fun ShopScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Shop",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Shop with a purpose - Every purchase supports animals in need",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.ShoppingCart, null, tint = Primary, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "SAID Shop",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Visit our shop to find unique items, merchandise, and pre-loved goods. All proceeds go directly to animal care and welfare programs.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { /* Open shop website */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Visit Online Shop", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Popular Items",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ShopItem("SAID Merchandise", "T-shirts, hoodies, caps, and more")
                        ShopItem("Socks Collection", "Comfortable socks with animal designs")
                        ShopItem("Pre-Loved Items", "Quality second-hand items")
                        ShopItem("Gift Items", "Perfect gifts for animal lovers")
                    }
                }
            }
        }
    }
}

// Dancing Donkey Screen - MOVED TO DancingDonkeyScreen.kt
// @Composable
// fun DancingDonkeyScreen() { ... }

// Heart & Sole Store Screen
@Composable
fun HeartSoleStoreScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Heart & Sole Store",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Step into comfort and purpose",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "About Heart & Sole",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Every pair of socks you purchase from Heart & Sole directly supports animals in need. These comfortable, quality socks help us raise funds for veterinary care, mobile clinics, and community education.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Shop now and make a difference with every step!",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Primary
                    )
                }
            }
        }
    }
}

// Golf Day 2025 Screen
@Composable
fun GolfDay2025Screen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Golf Day 2025",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Join us for our annual fundraising golf day",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Event, null, tint = Primary, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Event Details",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Our annual Golf Day is a premier fundraising event that brings together golf enthusiasts and animal lovers. All proceeds go towards animal welfare and community outreach programs.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Stay tuned for 2025 event details!",
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                        color = Primary
                    )
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = { /* Navigate to contact */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Register Interest", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

// Helper Composable
@Composable
private fun ShopItem(title: String, description: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Icon(Icons.Default.CheckCircle, null, tint = Success, modifier = Modifier.size(20.dp).padding(top = 2.dp))
        Spacer(Modifier.width(12.dp))
        Column {
            Text(
                title,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                fontSize = 15.sp
            )
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MutedForeground,
                fontSize = 13.sp
            )
        }
    }
}


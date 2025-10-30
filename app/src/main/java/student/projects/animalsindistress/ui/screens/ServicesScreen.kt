package student.projects.animalsindistress.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import student.projects.animalsindistress.ui.theme.*

@Composable
fun ServicesScreen() {
    val context = LocalContext.current
    var showRequestForm by remember { mutableStateOf(false) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Our Services",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Professional veterinary care built on focused animal care education within marginalised communities",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // Emergency Contact Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .background(
                        brush = Brush.horizontalGradient(colors = listOf(Primary, PrimaryGlow)),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(20.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Emergency Veterinary Care",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = PrimaryForeground,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Available 24/7 for urgent animal care needs. Don't hesitate to call.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = PrimaryForeground.copy(alpha = 0.9f),
                        textAlign = TextAlign.Center,
                        fontSize = 13.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0113142252"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Primary
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Phone, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Call Emergency Line", fontWeight = FontWeight.SemiBold)
                        }
                        OutlinedButton(
                            onClick = { showRequestForm = true },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder.copy(
                                width = 2.dp,
                                brush = Brush.linearGradient(listOf(Color.White, Color.White))
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Favorite, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text("Request Medical Aid", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
        
        // Our Locations Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Our Locations",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(12.dp))
                
                // Main Hospital Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Card(
                            modifier = Modifier.size(48.dp),
                            colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Favorite, null, tint = Primary, modifier = Modifier.size(24.dp))
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "SAID Midrand Hospital",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "1A Kyalami View Road, Midrand, 1685",
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedForeground,
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                TimeRow("Mon-Fri: 8:00 AM - 5:00 PM")
                                TimeRow("Sat: 8:00 AM - 1:00 PM")
                                TimeRow("Sun: Emergency only")
                            }
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = { /* Open maps */ },
                                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.Place, null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Get Directions", fontSize = 13.sp)
                            }
                        }
                    }
                }
                
                // Mobile Units Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp)) {
                        Card(
                            modifier = Modifier.size(48.dp),
                            colors = CardDefaults.cardColors(containerColor = Accent.copy(alpha = 0.2f)),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.People, null, tint = Accent, modifier = Modifier.size(24.dp))
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Mobile Veterinary Units",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Serving communities across Gauteng province",
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedForeground,
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Our mobile units visit communities on scheduled days. Contact us to check when we'll be in your area.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MutedForeground,
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = { showRequestForm = true },
                                colors = ButtonDefaults.buttonColors(containerColor = Accent),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                            ) {
                                Icon(Icons.Default.Favorite, null, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(6.dp))
                                Text("Request Mobile Visit", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
        
        // Services We Provide Section
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Services We Provide",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 20.sp
                )
                Spacer(Modifier.height(12.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    ServiceCard(
                        icon = Icons.Default.Favorite,
                        iconColor = Success,
                        title = "General Veterinary Care",
                        description = "Comprehensive health checks, treatments, and preventive care for all animals."
                    )
                    ServiceCard(
                        icon = Icons.Default.Shield,
                        iconColor = Primary,
                        title = "Vaccination Programs",
                        description = "Essential vaccinations to keep your animals healthy and prevent disease outbreaks."
                    )
                    ServiceCard(
                        icon = Icons.Default.Star,
                        iconColor = Accent,
                        title = "Surgical Procedures",
                        description = "Both routine and emergency surgical interventions with modern equipment."
                    )
                    ServiceCard(
                        icon = Icons.Default.People,
                        iconColor = Secondary,
                        title = "Community Outreach",
                        description = "Regular visits to underserved communities providing on-site veterinary care."
                    )
                }
            }
        }
        
        // CTA Button
        item {
            Button(
                onClick = { showRequestForm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Primary
                ),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(
                    "Request Medical Aid",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 16.sp
                )
            }
        }
    }
    
    // Request Form Dialog
    if (showRequestForm) {
        RequestMedicalAidDialog(onDismiss = { showRequestForm = false })
    }
}

@Composable
private fun TimeRow(text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.Schedule, null, modifier = Modifier.size(14.dp), tint = MutedForeground)
        Spacer(Modifier.width(6.dp))
        Text(text, style = MaterialTheme.typography.bodySmall, fontSize = 12.sp)
    }
}

@Composable
private fun ServiceCard(
    icon: ImageVector,
    iconColor: Color,
    title: String,
    description: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            Card(
                modifier = Modifier.size(40.dp),
                colors = CardDefaults.cardColors(containerColor = iconColor.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(20.dp)
            ) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
                }
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                    fontSize = 15.sp
                )
                Spacer(Modifier.height(4.dp))
        Text(
                    description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedForeground,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun RequestMedicalAidDialog(onDismiss: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var animalType by remember { mutableStateOf("") }
    var urgency by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Request Medical Aid",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Default.Close, null)
                    }
                }
                
                Spacer(Modifier.height(16.dp))
                
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Your Name") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location/Address") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Describe the Problem") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3,
                    placeholder = { Text("Please describe the animal's condition...") }
                )
                
                Spacer(Modifier.height(16.dp))
                
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = { /* Handle submit */ onDismiss() },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary)
                    ) {
                        Text("Submit Request")
                    }
                }
            }
        }
    }
}

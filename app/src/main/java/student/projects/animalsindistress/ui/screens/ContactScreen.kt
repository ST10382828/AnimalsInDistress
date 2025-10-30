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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ContactScreen() {
    val context = LocalContext.current
    
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var subjectExpanded by remember { mutableStateOf(false) }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Section
        item {
            Text(
                "Contact Us",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                color = Foreground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Inquiries Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Inquiries",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Icon(
                            Icons.Default.Phone,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                "Phone: 011 466 0261",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                "Mobile: 083 640 8825",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MutedForeground
                            )
                        }
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Email,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Email: animals@animalsindistress.org.za",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                    }
                }
            }
        }
        
        // Visit Us Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Visit Us",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    
                    // Address
                    Row(
                        verticalAlignment = Alignment.Top,
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Icon(
                            Icons.Default.Place,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp).padding(top = 2.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                "Address",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Text(
                                "The Society for Animals in Distress\nMidrand, South Africa",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MutedForeground
                            )
                        }
                    }
                    
                    // Operating Hours
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            Icons.Default.Schedule,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(20.dp).padding(top = 2.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text(
                                "Operating Hours",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                            Spacer(Modifier.height(4.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text("Monday: 07:00 - 16:00", style = MaterialTheme.typography.bodySmall, color = MutedForeground)
                                Text("Tuesday: 07:00 - 16:00", style = MaterialTheme.typography.bodySmall, color = MutedForeground)
                                Text("Wednesday: 07:00 - 16:00", style = MaterialTheme.typography.bodySmall, color = MutedForeground)
                                Text("Thursday: 07:00 - 16:00", style = MaterialTheme.typography.bodySmall, color = MutedForeground)
                                Text("Friday: 07:00 - 16:00", style = MaterialTheme.typography.bodySmall, color = MutedForeground)
                                Text("Saturday: 09:00 - 12:00", style = MaterialTheme.typography.bodySmall, color = MutedForeground)
                                Text("Sunday: Closed", style = MaterialTheme.typography.bodySmall, color = MutedForeground)
                            }
                        }
                    }
                }
            }
        }
        
        // Social Media Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Tweet, post and howl about our mission",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Follow us on social media to stay updated with our latest news and events.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1877F2))
                        ) {
                            Text("Facebook", fontSize = 14.sp)
                        }
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/SaidFundraiser"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1DA1F2))
                        ) {
                            Text("Twitter", fontSize = 14.sp)
                        }
                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE1306C))
                        ) {
                            Text("Instagram", fontSize = 14.sp)
                        }
                    }
                }
            }
        }
        
        // Contact Form Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
        Text(
                        "Send us a Message",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    
                    // First Name & Last Name Row
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("First Name *", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                            Spacer(Modifier.height(4.dp))
                            OutlinedTextField(
                                value = firstName,
                                onValueChange = { firstName = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Last Name *", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                            Spacer(Modifier.height(4.dp))
                            OutlinedTextField(
                                value = lastName,
                                onValueChange = { lastName = it },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    // Email
                    Text("Email Address *", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                    
                    Spacer(Modifier.height(12.dp))
                    
                    // Phone
                    Text("Phone Number", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
        )

        Spacer(Modifier.height(12.dp))

                    // Subject Dropdown
                    Text("Subject *", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                    Spacer(Modifier.height(4.dp))
                    ExposedDropdownMenuBox(
                        expanded = subjectExpanded,
                        onExpandedChange = { subjectExpanded = it }
                    ) {
                        OutlinedTextField(
                            value = subject,
                            onValueChange = {},
                            readOnly = true,
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = subjectExpanded) },
                            placeholder = { Text("Select a subject") }
                        )
                        ExposedDropdownMenu(
                            expanded = subjectExpanded,
                            onDismissRequest = { subjectExpanded = false }
                        ) {
                            listOf("General Inquiry", "Donation", "Volunteering", "Adoption", "Emergency", "Other").forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        subject = option
                                        subjectExpanded = false
                                    }
                                )
                            }
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    // Message
                    Text("Message *", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium))
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = message,
                        onValueChange = { message = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 6,
                        placeholder = { Text("Please provide details about your inquiry...") }
                    )

        Spacer(Modifier.height(16.dp))

                    // Submit Button
                    Button(
                        onClick = { /* Handle submit */ },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = Primary),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text("Send Message", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }
    }
}

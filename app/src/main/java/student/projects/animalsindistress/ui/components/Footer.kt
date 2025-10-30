package student.projects.animalsindistress.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.Muted
import student.projects.animalsindistress.ui.theme.MutedForeground
import student.projects.animalsindistress.ui.theme.Primary

@Composable
fun Footer() {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Muted)
            .padding(16.dp)
    ) {
        // Three column layout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Column 1: Description + Social media
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(
                    "About Us",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "The Society for Animals in Distress provides professional veterinary care that is built on a foundation of focused animal care education within marginalised communities.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedForeground,
                    fontSize = 12.sp
                )
                Spacer(Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/tsfaid"))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.Face, "Facebook", tint = MutedForeground)
                    }
                    IconButton(
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/thesocietyforanimalsindistress/"))
                            context.startActivity(intent)
                        },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(Icons.Default.AccountCircle, "Instagram", tint = MutedForeground)
                    }
                }
            }
            
            // Column 2: Navigation
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    "Navigation",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                FooterLink("About Us")
                FooterLink("Contact")
                FooterLink("Donate")
                FooterLink("Partners")
            }
            
            // Column 3: Quick Links
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    "Quick Links",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                FooterLink("Shop")
                FooterLink("FAQ")
                FooterLink("News")
                FooterLink("Privacy Policy")
            }
        }
        
        // Newsletter Section
        Spacer(Modifier.height(24.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(24.dp))
        
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(
                "Newsletter",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(Modifier.height(8.dp))
            Text(
                "Stay updated with our latest news and events",
                style = MaterialTheme.typography.bodySmall,
                color = MutedForeground,
                fontSize = 12.sp
            )
            Spacer(Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Enter your email", fontSize = 14.sp) },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Button(
                    onClick = { /* Handle subscribe */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Primary)
                ) {
                    Text("Subscribe", fontSize = 14.sp)
                }
            }
        }
        
        // Bottom Bar
        Spacer(Modifier.height(24.dp))
        HorizontalDivider(color = MaterialTheme.colorScheme.outline)
        Spacer(Modifier.height(16.dp))
        
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        Text(
                "2025 © Society For Animals In Distress | All Rights Reserved",
            style = MaterialTheme.typography.bodySmall,
                color = MutedForeground,
                fontSize = 12.sp
        )
            Spacer(Modifier.height(4.dp))
        Text(
                "Designed and Managed By Umbani Software",
            style = MaterialTheme.typography.bodySmall,
                color = MutedForeground,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun FooterLink(text: String) {
    Text(
        text,
        style = MaterialTheme.typography.bodySmall,
        color = MutedForeground,
        fontSize = 12.sp,
        modifier = Modifier
            .clickable { /* Handle navigation */ }
            .padding(vertical = 4.dp)
    )
}




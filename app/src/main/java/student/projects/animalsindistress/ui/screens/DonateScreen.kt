package student.projects.animalsindistress.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

@Composable
fun DonateScreen() {
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Section
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Make a Donation",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Any contribution, no matter how big or small, can have a profound impact on the lives of animals in need.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }
        }
        
        // Bank Transfer Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.CreditCard,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Bank Transfer",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            fontSize = 20.sp
                        )
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Bank Details:",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Muted),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            BankDetailRow("Bank:", "Standard Bank")
                            BankDetailRow("Account Name:", "Society for Animals in Distress")
                            BankDetailRow("Account Number:", "[Contact us for details]")
                            BankDetailRow("Branch Code:", "[Contact us for details]")
                            BankDetailRow("Reference:", "Your name + DONATION")
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Please use your name + \"DONATION\" as the reference when making a transfer.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MutedForeground,
                        fontSize = 13.sp
                    )
                }
            }
        }
        
        // Credit Card Donation Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.PhoneAndroid,
                            contentDescription = null,
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            "Credit Card Donation",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            fontSize = 20.sp
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Make a secure donation using your credit card through our online payment system.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { /* Handle R100 */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Text("Donate R100", fontSize = 14.sp)
                            }
                            Button(
                                onClick = { /* Handle R500 */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Text("Donate R500", fontSize = 14.sp)
                            }
                        }
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { /* Handle R1000 */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Text("Donate R1000", fontSize = 14.sp)
                            }
                            Button(
                                onClick = { /* Handle Custom */ },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                Text("Custom Amount", fontSize = 14.sp)
                            }
                        }
                    }
                }
            }
        }
        
        // Monthly Debit Order Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Monthly Debit Order",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Set up a monthly debit order to provide ongoing support for our animals. Even small monthly contributions make a big difference.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Benefits: Regular, predictable income helps us plan better care for our animals and ensures we can provide consistent support to communities in need.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Success.copy(alpha = 0.9f),
                                fontSize = 13.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = { /* Handle debit order */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary)
                    ) {
                        Text("Set Up Monthly Debit Order")
                    }
                }
            }
        }
        
        // Contact Information Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Need Help?",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:0114660261"))
                            context.startActivity(intent)
                        }
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
                    Spacer(Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable {
                            val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:animals@animalsindistress.org.za"))
                            context.startActivity(intent)
                        }
                    ) {
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
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Our team is available to help you with any questions about donations or to provide additional information about our work.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                }
            }
        }
        
        // Tax Benefits Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Tax Benefits",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "The Society for Animals in Distress is a registered NPO and PBO, which means your donations may be tax-deductible.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "18A Tax Certificate: We provide 18A tax certificates for qualifying donations, which can be used to reduce your taxable income.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Success.copy(alpha = 0.9f),
                                fontSize = 13.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = { /* Handle request */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Accent)
                    ) {
                        Text("Request Tax Certificate")
                    }
                }
            }
        }
        
        // Your Impact Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Your Impact",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        ImpactRow("R100", "Feeds a dog for a week")
                        ImpactRow("R500", "Covers basic veterinary treatment")
                        ImpactRow("R1000", "Supports mobile clinic outreach")
                        ImpactRow("R5000", "Enables emergency surgery")
                    }
                }
            }
        }
        
        // Other Ways to Help Section
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Other Ways to Help",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        HelpLink("→ Volunteer with us", onClick = { /* Navigate */ })
                        HelpLink("→ Shop with a purpose", onClick = { /* Navigate */ })
                        HelpLink("→ Donate pre-loved items", onClick = { /* Navigate */ })
                        HelpLink("→ Register with MySchool", onClick = { /* Navigate */ })
                    }
                }
            }
        }
    }
}

@Composable
private fun BankDetailRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            fontSize = 14.sp
        )
        Text(
            value,
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 14.sp
        )
    }
}

@Composable
private fun ImpactRow(amount: String, description: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Icon(
            Icons.Default.Favorite,
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column {
        Text(
                amount,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                fontSize = 14.sp
            )
        Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MutedForeground,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun HelpLink(text: String, onClick: () -> Unit) {
    Text(
        text,
        style = MaterialTheme.typography.bodyMedium,
        color = Primary,
        fontSize = 14.sp,
        modifier = Modifier.clickable(onClick = onClick)
    )
}

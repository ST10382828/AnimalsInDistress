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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

@Composable
fun EquineOutreachScreen() {
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
                    "Hope for Horses",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Transforming Lives in Tembisa's Coal Yards",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // Mission Statement
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "Our Equine Support Unit is on a mission to bring positive change to the lives of horses in the coal yards of Tembisa. Our focus is clear: to enhance the welfare of these majestic animals by providing essential care and support.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedForeground,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            }
        }
        
        // How We Make a Difference
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "How We Make a Difference",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    PillarCard(
                        icon = Icons.Default.Home,
                        title = "Shelter",
                        description = "For horses in the coal yards, finding shelter can be a daily struggle. That's why we're committed to building safe havens where they can find respite from the elements, ensuring they have a place to call home."
                    )
                    PillarCard(
                        icon = Icons.Default.MedicalServices,
                        title = "Healthcare",
                        description = "Every horse deserves access to quality healthcare. Through our program, we provide essential veterinary services, including farrier work, dental care, deworming, and vaccinations."
                    )
                    PillarCard(
                        icon = Icons.Default.Favorite,
                        title = "Rehabilitation",
                        description = "In times of injury or illness, horses need rest and rehabilitation to heal. We provide specialized care for injured or sick horses at our facility."
                    )
                    PillarCard(
                        icon = Icons.Default.People,
                        title = "Education",
                        description = "Education is key to ensuring the well-being of horses. We provide training to owners on basic care practices, equine health, and the use of appropriate, safe, and humane tack."
                    )
                }
            }
        }
        
        // How You Can Help
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "How You Can Help Neigh-borhood Heroes",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Together we can make a difference, one hoof at a time. Here are some ways you can help us support our equine friends.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    HelpCard(
                        icon = Icons.Default.AttachMoney,
                        title = "Extend Your Support",
                        description = "Your generosity has the power to change lives. By donating, you're directly impacting the well-being of horses in Tembisa's coal yards. Your contributions enable us to provide vital veterinary care, build shelters, and offer rehabilitation services to those in need.",
                        footer = "You can also donate second hand goods to our Barn Shop or tack that you are no longer using."
                    )
                    HelpCard(
                        icon = Icons.Default.People,
                        title = "Volunteer Your Time",
                        description = "Join us in our mission to make a difference. Whether you're lending a helping hand at our facility, assisting with fundraising efforts, or spreading awareness in your community, your time and energy can create lasting change for these horses.",
                        footer = null
                    )
                    HelpCard(
                        icon = Icons.Default.Share,
                        title = "Spread the Word",
                        description = "Help us amplify our message of hope. Share our mission with your friends, family, and colleagues, and encourage them to join us in our cause. Together, we can create a brighter future for horses in Tembisa's coal yards.",
                        footer = null
                    )
                }
            }
        }
        
        // Call to Action
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Join Our Stable Efforts Today",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Foreground,
                        textAlign = TextAlign.Center,
                        fontSize = 24.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Don't be a neigh-sayer, support Equine Outreach",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedForeground,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = { /* Navigate to donate */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text("Donate Now", fontWeight = FontWeight.SemiBold)
                        }
                        Button(
                            onClick = { /* Navigate to volunteer */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text("Volunteer", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun PillarCard(icon: ImageVector, title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = Primary, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(12.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Foreground,
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodyMedium,
                color = MutedForeground,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun HelpCard(
    icon: ImageVector,
    title: String,
    description: String,
    footer: String?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, null, tint = Primary, modifier = Modifier.size(24.dp))
                Spacer(Modifier.width(12.dp))
                Text(
                    title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                    color = Foreground,
                    fontSize = 18.sp
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodyMedium,
                color = MutedForeground,
                fontSize = 14.sp
            )
            if (footer != null) {
                Spacer(Modifier.height(8.dp))
                Text(
                    footer,
                    style = MaterialTheme.typography.bodySmall,
                    color = MutedForeground,
                    fontSize = 13.sp
                )
            }
        }
    }
}

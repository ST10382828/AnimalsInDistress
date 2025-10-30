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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

@Composable
fun EducationScreen() {
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
                    "Education",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Education is the cornerstone of The Society's approach to animal welfare",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // Introduction
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
                        "Poverty has a huge impact in the communities that our mobile units serve and it is only through educating people about animal care and backing up this education with accessible veterinary services, that we are able to make a meaningful and sustainable impact in the lives of animals and their owners.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedForeground,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                }
            }
        }
        
        // Our Educational Approach
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text(
                    "Our Educational Approach",
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
                    EducationPillarCard(
                        icon = Icons.Default.People,
                        title = "Community Education",
                        description = "We provide comprehensive education to community members on proper animal care practices, nutrition, health management, and responsible pet ownership."
                    )
                    EducationPillarCard(
                        icon = Icons.Default.School,
                        title = "Veterinary Training",
                        description = "Our Animal Health Technician (AHT) Mentorship Program trains para-veterinary professionals to provide essential veterinary care in underserved communities."
                    )
                    EducationPillarCard(
                        icon = Icons.Default.Lightbulb,
                        title = "Owner Empowerment",
                        description = "We empower animal owners with knowledge and resources to provide better care for their animals, creating lasting change in communities."
                    )
                    EducationPillarCard(
                        icon = Icons.Default.MenuBook,
                        title = "Youth Education",
                        description = "Teaching young people to respect and care for animals creates a foundation for compassion that extends to all living beings."
                    )
                    EducationPillarCard(
                        icon = Icons.Default.Shield,
                        title = "Health Awareness",
                        description = "We educate communities about disease prevention, vaccination importance, and the link between animal and human health."
                    )
                    EducationPillarCard(
                        icon = Icons.Default.Favorite,
                        title = "Welfare Standards",
                        description = "Promoting humane treatment of animals and teaching proper handling, housing, and care standards for all species."
                    )
                }
            }
        }
        
        // Educational Impact
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Educational Impact",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = Foreground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                        fontSize = 24.sp
                    )
                    Spacer(Modifier.height(16.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Measurable Results
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Measurable Results",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Foreground,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                ImpactBullet("Increased voluntary veterinary visits")
                                ImpactBullet("Higher responsibility donations for treatments")
                                ImpactBullet("Improved animal welfare practices")
                                ImpactBullet("Enhanced community collaboration")
                            }
                        }
                        
                        // Long-term Benefits
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "Long-term Benefits",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Foreground,
                                fontSize = 16.sp
                            )
                            Spacer(Modifier.height(12.dp))
                            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                ImpactBullet("Sustainable animal care practices", Secondary)
                                ImpactBullet("Reduced disease transmission", Secondary)
                                ImpactBullet("Stronger human-animal bonds", Secondary)
                                ImpactBullet("Community empowerment", Secondary)
                            }
                        }
                    }
                }
            }
        }
        
        // Call to Action
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "Support Our Educational Mission",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = Foreground,
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Education is the foundation of lasting change. Help us continue our educational programs in underserved communities.",
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
                        Text("Support Education", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                    Button(
                        onClick = { /* Navigate to volunteer */ },
                        colors = ButtonDefaults.buttonColors(containerColor = Secondary),
                        modifier = Modifier.weight(1f),
                        contentPadding = PaddingValues(vertical = 12.dp)
                    ) {
                        Text("Volunteer to Teach", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun EducationPillarCard(icon: ImageVector, title: String, description: String) {
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
private fun ImpactBullet(text: String, color: Color = Primary) {
    Row(verticalAlignment = Alignment.Top) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .padding(top = 6.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = MutedForeground,
            fontSize = 13.sp
        )
    }
}


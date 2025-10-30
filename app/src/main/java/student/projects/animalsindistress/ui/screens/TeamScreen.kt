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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

@Composable
fun TeamScreen() {
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
                    "Meet The Team",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Meet the wonderful team behind the SAID scenes that help keep the wheels turning.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // Meg Harvey
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(64.dp),
                            colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(32.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Person, null, tint = Primary, modifier = Modifier.size(32.dp))
                            }
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                "Meg Harvey",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                fontSize = 24.sp
                            )
                            Text(
                                "CEO/Board Member",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Primary
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Text(
                        "Meg has been involved in animal welfare for over a decade – with an undeniable passion and affinity to animals, she has dedicated the majority of her adult life to serving and protecting animals.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Text(
                        "Her career started at the NSPCA in an administration role where she soon qualified as an inspector and worked through the ranks of the organization – she then went on to manage a local SPCA.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    
                    Spacer(Modifier.height(16.dp))
                    
                    // Areas of Interest
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3B82F6).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.MenuBook, null, tint = Color(0xFF1E40AF), modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Areas of Interest",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color(0xFF1E40AF)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "She has a keen interest in law, human psychology, and the link between crimes against animals and crimes against humans, as well as the positive mental health impact that animals have in people.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF1E3A8A),
                                fontSize = 13.sp
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    // Personal Life
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Terrain, null, tint = Color(0xFF047857), modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Personal Life",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color(0xFF047857)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Meg is married and has a daughter (8) and son (2) who are both animal crazy, and a rescue cat and dog. In 2017, she summited Mt. Kilimanjaro while on a quest to raise funds and awareness for animal welfare in South Africa and enjoys mountaineering and the outdoors.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF065F46),
                                fontSize = 13.sp
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    // Philosophy
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF8B5CF6).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Favorite, null, tint = Color(0xFF6D28D9), modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(
                                    "Philosophy",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = Color(0xFF6D28D9)
                                )
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "She believes that the change that our country and world desperately needs starts with engendering compassion to all animals in the youth – that if young people were taught to respect every creature – they would in turn respect humans too.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF5B21B6),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
        
        // Dr Isabel Meyer
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(64.dp),
                            colors = CardDefaults.cardColors(containerColor = Secondary.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(32.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Business, null, tint = Secondary, modifier = Modifier.size(32.dp))
                            }
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                "Dr Isabel Meyer",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                fontSize = 24.sp
                            )
                            Text(
                                "Board Member",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Secondary
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Text(
                        "Isabel joined the society as board member in 2021.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Text(
                        "Isabel is a systems researcher and veterinarian, with a passion for finding ways to ensure that development efforts deliver impact and sustain their benefits. Her research includes community development projects, and sustainable welfare management strategies for animals at industrial sites.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    
                    Spacer(Modifier.height(16.dp))
                    
                    // Research Focus
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3B82F6).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Research Focus",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Color(0xFF1E40AF)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "She collaborates with universities to create opportunities for student research on animal health and welfare systems. Isabel considers access to veterinary services as critical to animal welfare and is keen to contribute to the Society's vision of access to care for animals in resource-constrained environments.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF1E3A8A),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
        
        // Andrew Fairlie
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(64.dp),
                            colors = CardDefaults.cardColors(containerColor = Accent.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(32.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Business, null, tint = Accent, modifier = Modifier.size(32.dp))
                            }
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                "Andrew Fairlie",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                fontSize = 24.sp
                            )
                            Text(
                                "Board Member",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Accent
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Text(
                        "Andrew joined the Society as Treasurer in mid-2019. Andrew is an Associate Member of CIMA and Professional Accountant (SA) with extensive experience in corporate finance management and strategy.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    
                    Spacer(Modifier.height(16.dp))
                    
                    // Professional Expertise
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Professional Expertise",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Color(0xFF047857)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "These experiences contribute to strategy, financial management and internal control oversight which ensure accuracy and transparency in financial reporting.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF065F46),
                                fontSize = 13.sp
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    // Personal Mission
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF8B5CF6).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Personal Mission",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Color(0xFF6D28D9)
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "Andrew is a family man and animal lover, dedicated to the upliftment of animals and people in impoverished communities and honoured to work alongside the heroes at the Society.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF5B21B6),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
        
        // Vacancy Card
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp),
                border = CardDefaults.outlinedCardBorder().copy(
                    width = 2.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(Primary.copy(alpha = 0.3f))
                )
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.size(64.dp),
                            colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(32.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.People, null, tint = Primary, modifier = Modifier.size(32.dp))
                            }
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text(
                                "Vacancy",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                fontSize = 24.sp
                            )
                            Text(
                                "Board Member Position Available",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Primary
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Text(
                        "We are seeking passionate individuals to join our Board of Directors and help drive our mission forward! If you have the skills, experience, and dedication to make a meaningful impact, we'd love to hear from you.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    
                    Spacer(Modifier.height(16.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "How to Apply",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Primary
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "To apply, please send your CV and a one-page motivation outlining how you can add value to SAID to:",
                                style = MaterialTheme.typography.bodySmall,
                                color = Primary,
                                fontSize = 13.sp
                            )
                            Spacer(Modifier.height(8.dp))
                            Text(
                                "meg@animalsindistress.org.za",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                color = Primary,
                                modifier = Modifier
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Join us in creating a brighter future for animals and communities!",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Success.copy(alpha = 0.9f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }
        }
    }
}

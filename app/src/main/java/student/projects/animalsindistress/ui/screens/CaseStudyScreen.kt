package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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

@Composable
fun CaseStudyScreen() {
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
                    "Case Study",
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(12.dp))
                Text(
                    "Breaking Barriers For Animals In Diepsloot Extensions",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    ),
                    color = Primary,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // About Diepsloot
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "About Diepsloot",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "A densely populated township in Gauteng, South Africa with a conservatively estimated population of between 350,000 and 400,000 people, living in a district divided into \"12 extensions\".",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "The Society has been serving the population of Diepsloot for 17 years.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "It is made up of brick houses built by landowners, a mix of public housing blocks, and shacks made of corrugated iron and plastic lining. Residents reliant on outbuildings for sanitation.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                }
            }
        }
        
        // Community Structure
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Community Structure",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BulletPointItem("45.5% of residents rent their property from a landowner who has subdivided their land")
                        BulletPointItem("While the main road is paved, the side streets are gravel, ditches, and stagnant waste. The informal streets are a maze")
                        BulletPointItem("The township is surrounded by open fields and farms, while the closest neighbourhoods are wealthy gated communities")
                    }
                }
            }
        }
        
        // The Problem
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "The Problem",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "The problem in Diepsloot was the critical need for veterinary care at the outset, confirmed by veterinary intervention need's assessments conducted by SAID veterinary professionals, engaged potential collaborators, and connected community actors to evaluate approaches.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Maintaining dog population size and demography in balance with human ideals with aims including reducing the number of unwanted dogs, keeping wanted dogs in a good state of health and welfare, and minimising risks presented by dogs to public health and other animals.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                    Spacer(Modifier.height(12.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Destructive.copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Box(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "Findings exposed thin, free-roaming, and hungry dogs and cats with ticks, uncontrolled breeding, unhealthy and unsafe environments, untreated injuries, prevalence of infectious diseases, most not immunised by vaccination. High numbers of free-roaming dogs a concern.",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Destructive.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
        
        // Vaccination Targets
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Vaccination Targets",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF3B82F6).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "70% Vaccination Coverage",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Color(0xFF1E40AF)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "According to the World Animal Health Association (OIE), there is much evidence that achieving 70% vaccination coverage, even where dog population turnover is high, is feasible but challenging.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF1E3A8A),
                                fontSize = 13.sp
                            )
                        }
                    }
                    
                    Spacer(Modifier.height(12.dp))
                    
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF10B981).copy(alpha = 0.1f)),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                "70-83% Sterilisation Target",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                                color = Color(0xFF047857)
                            )
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Sterilisation of at least 70% to 83% of females is often mentioned as a target to achieve for population reduction. A sustainable remedy for both disease and animal welfare problems posed by free-roaming dogs in developing countries.",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF065F46),
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
        
        // Key Issues
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Key Issues Identified",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        BulletPointItem("No access to affordable veterinary services in the community", Destructive)
                        BulletPointItem("Lack of knowledge amongst community members on how to better care of their animals", Destructive)
                        BulletPointItem("Diseases know no boundaries", Destructive)
                    }
                }
            }
        }
        
        // Success Indicators
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Success Indicators",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        SuccessCard(
                            "✓ Increased Improved Ownership",
                            "A notable indicator of people being more receptive to animal welfare education and collaboration"
                        )
                        SuccessCard(
                            "✓ Owner Engagement",
                            "A further indicator of care or 'responsibility' by owners is owner engagement in the intervention itself"
                        )
                        SuccessCard(
                            "✓ Increased Hospital Visits",
                            "Increase in the number of dogs voluntarily brought to The Society's hospital"
                        )
                        SuccessCard(
                            "✓ Responsibility Donations",
                            "Increase in the number of responsibility donations by animal owners for veterinary treatments"
                        )
                    }
                }
            }
        }
        
        // Program Impact
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Program Impact",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 18.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        ImpactItem("Significantly improved perceptions of the value of veterinary interventions")
                        ImpactItem("Strengthened South African Veterinary Services to deliver effective control")
                        ImpactItem("Significant change in attitude of dog and cat owners")
                        ImpactItem("Improved collaboration between The Society's veterinary services and local animal welfare organisations")
                    }
                }
            }
        }
        
        // Conclusion
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Primary.copy(alpha = 0.1f)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "Conclusion",
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                        color = Foreground,
                        fontSize = 22.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "The Society for Animals in Distress programmes have demonstrated that strategically targeted animal health, social interventions and partner funding can make a big impact.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedForeground,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "The programme is part of a continuing process at promoting best practices, transparency, and capacity of Veterinary Services to address animal health and welfare and public health problems.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground
                    )
                }
            }
        }
    }
}

@Composable
private fun BulletPointItem(text: String, color: Color = Primary) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .padding(top = 6.dp)
                .background(color, shape = RoundedCornerShape(4.dp))
        )
        Spacer(Modifier.width(12.dp))
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MutedForeground,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun SuccessCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Success.copy(alpha = 0.1f)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Success.copy(alpha = 0.9f),
                fontSize = 14.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = Success.copy(alpha = 0.8f),
                fontSize = 13.sp
            )
        }
    }
}

@Composable
private fun ImpactItem(text: String) {
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            "• ",
            style = MaterialTheme.typography.bodyMedium,
            color = MutedForeground
        )
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MutedForeground,
            modifier = Modifier.weight(1f)
        )
    }
}

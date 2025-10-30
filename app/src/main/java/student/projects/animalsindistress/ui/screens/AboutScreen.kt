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
fun AboutScreen() {
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
                "About Us",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                ),
                color = Foreground,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        // Introduction
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        "At the Society for Animals in Distress (SAID), we're more than just a veterinary care provider—we're a movement for change. Dedicated to improving the lives of animals and people alike, we bring professional veterinary care to pets and working animals in low- to no-income communities, ensuring they receive the help they deserve.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedForeground,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "But our mission doesn't stop there. We believe in building a kinder, more empathetic world from the ground up. That's why we focus on educating foundation-phase children, planting the seeds of empathy and compassion for animals at an early age. By shaping young hearts and minds, we're paving the way for a brighter future—one where care, respect, and responsibility define the relationship between humans and animals.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedForeground,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        "Powered by the generosity of donors, innovative income streams, our dedicated staff and, a passionate network of volunteers, SAID delivers life-changing services that create lasting impact. Every service we provide is guided by our commitment to excellence and tailored to the resources available, ensuring we maximize our ability to uplift both animals and the communities we serve.",
                        style = MaterialTheme.typography.titleMedium,
                        color = MutedForeground,
                        fontSize = 16.sp
                    )
                }
            }
        }
        
        // Our Values Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Our Values",
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
                    ValueCard("Professional Excellence")
                    ValueCard("Transparent Governance")
                    ValueCard("Accountable Service Provision")
                    ValueCard("Respect For The Dignity Of Others")
                }
            }
        }
        
        // Income Generation & Sustainability Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Income Generation & Sustainability",
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
                    "The Society for Animals in Distress provides professional veterinary care that is built on a foundation of focused animal care education within marginalised communities.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    IncomeStreamCard(
                        "Organic Compost Sales",
                        "Every Spring we sell our \"home grown\" organic compost to the public on a first come-first serve basis."
                    )
                    IncomeStreamCard(
                        "Golf Day & Sporting Events",
                        "SAID hosts an annual Golf Day and also partakes in other sporting events where our loyal supporters can run or cycle for a cause close to their hearts."
                    )
                    IncomeStreamCard(
                        "Pre-loved Goods Sales",
                        "We collect and resell your pre-loved goods and second-hand books to raise much needed funds for our animals."
                    )
                    IncomeStreamCard(
                        "Tin Collections",
                        "Thanks to our wonderful volunteers we have several weekend tin collection teams at specific shopping centres across Johannesburg. These teams gather monthly on a rotational basis to raise funds for our cause. We also have static tins placed strategically across Gauteng."
                    )
                    IncomeStreamCard(
                        "MySchool Partnership",
                        "The public is encouraged to register with MySchool and add us as a beneficiary. This is 100% free and costs you nothing. As you spend, we earn."
                    )
                    IncomeStreamCard(
                        "Product Catalogue",
                        "SAID has a catalogue of various new and second-hand products that we sell to the general public. The catalogue can be found on our website."
                    )
                    IncomeStreamCard(
                        "Property Rental",
                        "We are blessed to have a 4.2ha farm and with that we have a few older buildings that we have refurbished and currently rent out to other businesses. We aim to expand this further."
                    )
                    IncomeStreamCard(
                        "Veterinary Services",
                        "At the small animal hospital we generate funding by charging all NGOs a nominal fee for professional veterinary services. We also encourage our community members serviced by our mobile units to donate towards the treatment of their animals with the aim to promote responsible animal ownership and encourage an active interest in the alleviation of animal suffering. No treatment is ever refused if a client is unable to donate towards their pet's treatment."
                    )
                }
            }
        }
        
        // Our Projects & Programs Section
        item {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Our Projects & Programs",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(16.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            "Projects with a porpoise!",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = Primary,
                            fontSize = 22.sp
                        )
                        Spacer(Modifier.height(16.dp))
                        
                        // SINCE 2004
                        Text(
                            "SINCE 2004",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = Foreground,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Established in 2004, this project was intended to secure the health and wellbeing of equine in the Tembisa area of Ekurhuleni and Ivory Park, Johannesburg. The success of this project included the reduction of 360 working equine to sum 30 equine in 2020.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "With the support of The Society providing access to veterinary care, securing of paddocks, improved harnessing and farriery, the wellbeing of the animals, the owners' micro-enterprise income capacity was enhanced.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "The stabilisation of herds and the increased ability of owners to afford mechanisation resulted in the desired decline of working animals in unfit circumstances.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "With the decline of working equine from the Coal yard Project, the surrender of animals birthed the need to ensure that these equine remain valuable in secure homes.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground
                        )
                        
                        Spacer(Modifier.height(20.dp))
                        
                        // Diamonds from Dust
                        Text(
                            "Diamonds from Dust Program",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            color = Foreground,
                            fontSize = 18.sp
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "The Diamonds from Dust Program and the building of trust, re-programming and backing produces safe equine foster companions for adults and children and a lifetime of pamper and care. The program has also been instrumental in the selected protection of equine made vulnerable by the economic circumstance of their caring owners.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ValueCard(value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                value,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                color = Primary,
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
private fun IncomeStreamCard(title: String, description: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = Primary,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodyMedium,
                color = MutedForeground,
                fontSize = 14.sp
            )
        }
    }
}

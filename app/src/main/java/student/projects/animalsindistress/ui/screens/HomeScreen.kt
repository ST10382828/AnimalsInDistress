package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.components.ImportantAnnouncement
import student.projects.animalsindistress.ui.theme.*

@Composable
fun HomeScreen(onDonateClick: () -> Unit, onNavigate: (String) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(0.dp),
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        // Important Announcement - Commented out
        // item { ImportantAnnouncement() }
        
        // Hero Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Primary, PrimaryGlow)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 48.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "The voice for the voiceless",
                        color = PrimaryForeground,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text = "The Society for Animals in Distress provides professional veterinary care that is built on a foundation of focused animal care education within marginalised communities. We achieve this by focusing our efforts on our tri-pillar approach.",
                        color = PrimaryForeground.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp
                    )
                    Spacer(Modifier.height(24.dp))
                    Button(
                        onClick = onDonateClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Primary
                        ),
                        contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
                    ) {
                        Icon(Icons.Default.Favorite, null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Make a donation", fontWeight = FontWeight.SemiBold)
                    }
                }
            }
        }

        // What We Do Section
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "What We Do",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    InfoCard(
                        title = "Education",
                        body = "Education is the cornerstone of The Society's approach. Poverty has a huge impact in the communities that our mobile units serve and it is only through educating people about animal care and backing up this education with accessible veterinary services, that we are able to make a meaningful and sustainable impact in the lives of animals and their owners."
                    )
                    InfoCard(
                        title = "Professional Veterinary Care",
                        body = "The Society's facility in Midrand comprises a state of the art hospital where a Senior Veterinarian, para-veterinary professionals and support staff provide the highest standard of veterinary care, internal outreach programs and the mentorship of para-veterinary graduates. The facility incorporates 203 fit for purpose kennels, an equine care centre, paddocking for herd animals and equine, administration offices and storage facilities."
                    )
                    InfoCard(
                        title = "Eligibility To Receive Veterinary Services",
                        body = "Persons earning low to no income are welcome to make use of The Society's hospital services. All persons who have previously used a private veterinary clinic must produce a referral letter from the veterinarian of that practise. This referral letter will enable us to access the veterinary records critical to the patients care."
                    )
                }
            }
        }

        // Join the Purr-suit Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Join the Purr-suit",
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
                    text = "Together we can make a difference, one paw at a time.",
                    style = MaterialTheme.typography.titleMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Here are some ways you can help us help our furry friends.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    ActionCard(
                        title = "Donate Today",
                        description = "Any contribution, no matter how big or small, can have a profound impact on the lives of animals in need.",
                        actionText = "Make a Donation",
                        icon = Icons.Default.Favorite,
                        onClick = onDonateClick
                    )
                    ActionCard(
                        title = "Hop on board",
                        description = "We are always in need of dedicated volunteers to help us with various tasks. Whether it's walking dogs or socialising cats, your time is appreciated.",
                        actionText = "Contact Us",
                        icon = Icons.Default.Person,
                        onClick = { onNavigate("volunteer") }
                    )
                    ActionCard(
                        title = "Pawtner with us",
                        description = "Does your business share a love for animals, let's collaborate! We are always looking for sponsors, partners, and supporters who can help.",
                        actionText = "Contact Us",
                        icon = Icons.Default.Business,
                        onClick = { onNavigate("contact") }
                    )
                    ActionCard(
                        title = "Shop with a purrpose",
                        description = "Step into comfort and purpose! Every pair of socks you purchase directly supports animals in need. Shop now and make a difference with every step!",
                        actionText = "Shop Now",
                        icon = Icons.Default.ShoppingCart,
                        onClick = { onNavigate("shop") }
                    )
                    ActionCard(
                        title = "Roar the word",
                        description = "Tweet, post and howl about our mission to help animals. Word of snout can make a huge difference.",
                        actionText = "Share Our Mission",
                        icon = Icons.Default.Share,
                        onClick = { onNavigate("contact") }
                    )
                    ActionCard(
                        title = "Paws for a cause",
                        description = "Join the flock and attend our fundraising events and help furry and feathered friends soar. Keep an eye on our latest news or social media platforms for upcoming events.",
                        actionText = "View Events",
                        icon = Icons.Default.Event,
                        onClick = { onNavigate("news") }
                    )
                }
            }
        }

        // Latest Mews Section
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Muted.copy(alpha = 0.3f))
                    .padding(16.dp)
            ) {
                Text(
                    text = "latest Mews",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(Modifier.height(24.dp))
                
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Em-bark on a mission",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Our mission is to provide professional veterinary services in underprivileged areas and ensure the welfare of animals everywhere.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "You can be a part of our journey!",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Get in touch to see how you can help today",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MutedForeground,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(20.dp))
                        Button(
                            onClick = { onNavigate("contact") },
                            colors = ButtonDefaults.buttonColors(containerColor = Primary),
                            contentPadding = PaddingValues(horizontal = 32.dp, vertical = 12.dp)
                        ) {
                            Text("Contact Us", fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        // Final CTA Section
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(Secondary, Accent)
                        )
                    )
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Together we can make a difference, one paw at a time.",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(12.dp))
                    Text(
                        text = "Join our mission—donate, volunteer, or spread the word.",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(20.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxWidth(0.9f)
                    ) {
                        Button(
                            onClick = onDonateClick,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Primary
                            ),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Icon(Icons.Default.Favorite, null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(6.dp))
                            Text("Donate", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        }
                        OutlinedButton(
                            onClick = { onNavigate("contact") },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                            border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp, brush = Brush.linearGradient(listOf(Color.White, Color.White))),
                            modifier = Modifier.weight(1f),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Text("Contact Us", fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoCard(title: String, body: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                fontSize = 18.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                body,
                style = MaterialTheme.typography.bodyMedium,
                color = MutedForeground,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    description: String,
    actionText: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                fontSize = 18.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                description,
                style = MaterialTheme.typography.bodyMedium,
                color = MutedForeground,
                fontSize = 14.sp
            )
            Spacer(Modifier.height(12.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(onClick = onClick)
            ) {
                Text(
                    actionText,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                    color = Primary,
                    fontSize = 14.sp
                )
                Spacer(Modifier.width(4.dp))
                Icon(
                    Icons.Default.ArrowForward,
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}



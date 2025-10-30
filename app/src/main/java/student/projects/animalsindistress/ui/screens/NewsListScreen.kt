package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
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

data class NewsArticle(
    val id: String,
    val title: String,
    val date: String,
    val excerpt: String
)

@Composable
fun NewsListScreen(onOpen: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Hero Section
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "News",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 28.sp
                    ),
                    color = Foreground,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Latest news and updates from SAID",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MutedForeground,
                    textAlign = TextAlign.Center
                )
            }
        }
        
        // News Articles
        items(newsArticles) { article ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOpen(article.id) },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Text(
                            article.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = Foreground,
                            fontSize = 18.sp,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(Modifier.width(12.dp))
                        Text(
                            article.date,
                            style = MaterialTheme.typography.bodySmall,
                            color = MutedForeground,
                            fontSize = 12.sp
                        )
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        article.excerpt,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MutedForeground,
                        fontSize = 14.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickable { onOpen(article.id) }
                    ) {
                        Text(
                            "Read more",
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
    }
}

private val newsArticles = listOf(
    NewsArticle(
        id = "2021-07-21-three-dogs-killed-others-rescued-in-plastic-view-fire-pretoria-rekord",
        title = "THREE DOGS KILLED, OTHERS RESCUED IN PLASTIC VIEW FIRE – PRETORIA REKORD",
        date = "July 21, 2021",
        excerpt = "Tragic fire incident in Plastic View where three dogs were killed but others were successfully rescued by emergency services."
    ),
    NewsArticle(
        id = "2021-07-19-hond-gered-na-skoot-in-plundery",
        title = "HOND GERED NA SKOOT IN PLUNDERY",
        date = "July 19, 2021",
        excerpt = "Dog rescued after being shot during a robbery incident in Plundery area."
    ),
    NewsArticle(
        id = "2021-07-11-society-for-animals-in-distress-works-to-get-cats-sterilised",
        title = "SOCIETY FOR ANIMALS IN DISTRESS WORKS TO GET CATS STERILISED",
        date = "July 11, 2021",
        excerpt = "SAID continues its important work in cat sterilization programs to help control the animal population."
    ),
    NewsArticle(
        id = "2021-06-29-appointment-of-new-general-manager",
        title = "APPOINTMENT OF NEW GENERAL MANAGER",
        date = "June 29, 2021",
        excerpt = "SAID announces the appointment of a new General Manager to lead the organization forward."
    ),
    NewsArticle(
        id = "2021-04-25-said-rotary-centenary-certificate",
        title = "SAID Rotary Centenary Certificate",
        date = "April 25, 2021",
        excerpt = "SAID receives recognition from Rotary International for its centenary achievements."
    ),
    NewsArticle(
        id = "2021-03-31-said-career-opportunity-executive-general-manager",
        title = "SAID Career Opportunity - Executive General Manager",
        date = "March 31, 2021",
        excerpt = "Career opportunity announcement for Executive General Manager position at SAID."
    ),
    NewsArticle(
        id = "2021-03-31-said-different-org-campaign",
        title = "SAID Different.Org Campaign",
        date = "March 31, 2021",
        excerpt = "Launch of SAID's Different.Org campaign to raise awareness and support."
    ),
    NewsArticle(
        id = "2021-02-17-heather-resignation",
        title = "Heather Resignation",
        date = "February 17, 2021",
        excerpt = "Announcement regarding Heather's resignation from SAID."
    ),
    NewsArticle(
        id = "2020-11-30-said-christmas-communication-2020",
        title = "SAID Christmas Communication 2020",
        date = "November 30, 2020",
        excerpt = "Special Christmas message and updates from SAID for 2020."
    ),
    NewsArticle(
        id = "2020-11-12-marchig-150-animal-sterilisation-campaign",
        title = "Marchig 150 Animal Sterilisation Campaign",
        date = "November 12, 2020",
        excerpt = "Successful completion of the Marchig 150 animal sterilization campaign."
    ),
    NewsArticle(
        id = "2020-10-20-animal-health-technician-mentorship-program",
        title = "Animal Health Technician Mentorship Program",
        date = "October 20, 2020",
        excerpt = "Launch of mentorship program for animal health technicians."
    ),
    NewsArticle(
        id = "2020-09-30-donor-update-september-2020",
        title = "Donor Update September 2020",
        date = "September 30, 2020",
        excerpt = "Monthly update to donors about SAID's activities and impact."
    ),
    NewsArticle(
        id = "2020-07-09-mandela-day-2020",
        title = "Mandela Day 2020",
        date = "July 9, 2020",
        excerpt = "SAID's participation in Mandela Day 2020 activities and community service."
    ),
    NewsArticle(
        id = "2020-07-09-donor-thank-you-may-2020",
        title = "Donor Thank You May 2020",
        date = "July 9, 2020",
        excerpt = "Special thank you message to donors for their support during May 2020."
    ),
    NewsArticle(
        id = "2020-03-24-covid19-announcement",
        title = "COVID-19 Announcement",
        date = "March 24, 2020",
        excerpt = "Important announcement regarding SAID's response to COVID-19 pandemic."
    ),
    NewsArticle(
        id = "2020-03-01-newsletter-march-2020",
        title = "Newsletter March 2020",
        date = "March 1, 2020",
        excerpt = "Monthly newsletter with updates on SAID's activities and programs."
    ),
    NewsArticle(
        id = "2019-11-04-guy-fawkes-2019",
        title = "Guy Fawkes 2019",
        date = "November 4, 2019",
        excerpt = "SAID's message about pet safety during Guy Fawkes celebrations."
    ),
    NewsArticle(
        id = "2019-10-31-notice-of-agm-2019",
        title = "Notice of AGM 2019",
        date = "October 31, 2019",
        excerpt = "Notice of Annual General Meeting for 2019."
    ),
    NewsArticle(
        id = "2019-09-13-newsletter-september-2019",
        title = "Newsletter September 2019",
        date = "September 13, 2019",
        excerpt = "Monthly newsletter with September 2019 updates."
    ),
    NewsArticle(
        id = "2019-08-13-alert-dog-poisoning",
        title = "Alert Dog Poisoning",
        date = "August 13, 2019",
        excerpt = "Important alert about dog poisoning incidents in the area."
    ),
    NewsArticle(
        id = "2019-04-18-newsletter-april-2019",
        title = "Newsletter April 2019",
        date = "April 18, 2019",
        excerpt = "Monthly newsletter with April 2019 updates and news."
    )
)

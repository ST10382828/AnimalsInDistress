package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

data class NewsContent(val title: String, val date: String, val paragraphs: List<String>)

private val newsBySlug: Map<String, NewsContent> = mapOf(
    "2021-07-21" to NewsContent(
        title = "Three dogs killed, others rescued in Plastic View fire",
        date = "21 July 2021",
        paragraphs = listOf(
            "Emergency teams and welfare groups responded to a devastating fire in Plastic View.",
            "The Society for Animals in Distress assisted with rescue, treatment and relief."
        )
    ),
    "2021-06-29" to NewsContent(
        title = "Appointment of new general manager",
        date = "29 June 2021",
        paragraphs = listOf(
            "The Society announced the appointment of a new general manager to lead operations.",
            "This strengthens governance and impact across programmes."
        )
    )
)

@Composable
fun NewsDetailScreen(slug: String) {
    val content = newsBySlug[slug]
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = content?.title ?: "News Article",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        if (content != null) {
            Text(text = content.date, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 6.dp))
            Spacer(Modifier.height(12.dp))
            content.paragraphs.forEach { p ->
                Text(text = p, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
            }
        } else {
            Text(
                text = "Article not found.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
        }
    }
}



package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProgramsScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Our Programs",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = "Equine Support Unit", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        Text(text = "Shelter builds, farrier and dental care, deworming and vaccinations, rehabilitation, and owner education.", style = MaterialTheme.typography.bodyMedium)

        Text(text = "Coal Yard Project", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
        Text(text = "Improving welfare for working equines in Tembisa’s coal yards through targeted veterinary and infrastructure support.", style = MaterialTheme.typography.bodyMedium)

        Text(text = "Diamonds From The Dust", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
        Text(text = "Celebrating transformational recoveries of animals through care, rehabilitation, and compassion.", style = MaterialTheme.typography.bodyMedium)

        Text(text = "AHT Mentorship Program", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
        Text(text = "Mentoring para-veterinary professionals and strengthening community-based veterinary capacity.", style = MaterialTheme.typography.bodyMedium)
    }
}



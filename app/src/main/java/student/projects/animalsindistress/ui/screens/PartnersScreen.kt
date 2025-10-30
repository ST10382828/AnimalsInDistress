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
fun PartnersScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Partners",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(text = "Corporate & Community Partners", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 8.dp))
        Text(text = "We collaborate with organisations who share our commitment to animal welfare and education.", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Ways to Partner", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 12.dp))
        Text(text = "• Sponsorships for programmes and campaigns", style = MaterialTheme.typography.bodyMedium)
        Text(text = "• In-kind donations (goods, services, venues)", style = MaterialTheme.typography.bodyMedium)
        Text(text = "• Employee volunteering and payroll giving", style = MaterialTheme.typography.bodyMedium)
    }
}



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

@Composable
fun FAQScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Frequently Asked Questions",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(Modifier.height(12.dp))
        Text(text = "Q: What are your operating hours?", style = MaterialTheme.typography.titleSmall)
        Text(text = "A: Monday–Friday 07:00–16:00, Saturday 09:00–12:00, Sunday Closed.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text(text = "Q: Who can use your services?", style = MaterialTheme.typography.titleSmall)
        Text(text = "A: Persons earning low to no income. Referral letter required if previously used private veterinary clinic.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text(text = "Q: How can I volunteer?", style = MaterialTheme.typography.titleSmall)
        Text(text = "A: Contact us at animals@animalsindistress.org.za or call 011 466 0261 to discuss opportunities.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text(text = "Q: Do you provide tax certificates?", style = MaterialTheme.typography.titleSmall)
        Text(text = "A: Yes, we provide Section 18A tax certificates for qualifying donations. Email us to request.", style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(8.dp))
        Text(text = "Q: Where is your facility located?", style = MaterialTheme.typography.titleSmall)
        Text(text = "A: Our state-of-the-art hospital is located in Midrand with 203 kennels and equine care facilities.", style = MaterialTheme.typography.bodyMedium)
    }
}



package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import student.projects.animalsindistress.ui.theme.*

@Composable
fun NotFoundScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            "404",
            style = MaterialTheme.typography.displayLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 64.sp
            ),
            color = Primary
        )
        Spacer(Modifier.height(24.dp))
        Text(
            "Page Not Found",
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            color = Foreground,
            fontSize = 28.sp
        )
        Spacer(Modifier.height(12.dp))
        Text(
            "Sorry, we couldn't find the page you're looking for. It might have been moved or doesn't exist.",
            style = MaterialTheme.typography.titleMedium,
            color = MutedForeground,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(0.8f)
        )
        Spacer(Modifier.height(24.dp))
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick = { /* Navigate to home */ },
                colors = ButtonDefaults.buttonColors(containerColor = Primary),
                modifier = Modifier.fillMaxWidth(0.6f),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Icon(Icons.Default.Home, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Go Home", fontWeight = FontWeight.SemiBold)
            }
            OutlinedButton(
                onClick = { /* Navigate back */ },
                modifier = Modifier.fillMaxWidth(0.6f),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
            ) {
                Icon(Icons.Default.ArrowBack, null, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(8.dp))
                Text("Go Back", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}


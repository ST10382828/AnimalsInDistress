package student.projects.animalsindistress.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import student.projects.animalsindistress.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun GalleryScreen() {
    var activeCategory by remember { mutableStateOf("all") }
    
    val images = when (activeCategory) {
        "memory" -> memoryImages
        "animals" -> animalsImages
        "volunteers" -> volunteersImages
        "diamonds" -> diamondsImages
        "horses" -> horsesImages
        "events" -> eventsImages
        else -> (memoryImages + animalsImages + volunteersImages + 
                 diamondsImages + horsesImages + eventsImages)
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Compact Header
        Text(
            "Gallery",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            ),
            color = Foreground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        
        // Compact Category Filter - Using FlowRow to wrap chips
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            maxItemsInEachRow = Int.MAX_VALUE
        ) {
            galleryCategories.forEach { (key, label) ->
                FilterChip(
                    selected = activeCategory == key,
                    onClick = { activeCategory = key },
                    label = { Text(label, fontSize = 11.sp) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = Primary,
                        selectedLabelColor = PrimaryForeground,
                        containerColor = Color.White,
                        labelColor = MutedForeground
                    ),
                    modifier = Modifier.height(28.dp)
                )
            }
        }
        
        // Gallery Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(images) { imageUrl ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    colors = CardDefaults.cardColors(containerColor = Muted),
                    shape = RoundedCornerShape(8.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Gallery image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

private val galleryCategories = linkedMapOf(
    "all" to "All Photos",
    "memory" to "Memory",
    "animals" to "Animals",
    "volunteers" to "Volunteers",
    "diamonds" to "Diamonds",
    "horses" to "Horse Care",
    "events" to "Events"
)

private val memoryImages = listOf(
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery1.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery2.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery3.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery4.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery5.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery6.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery7.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery8.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery9.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Memory-Gallery10.png"
)

private val animalsImages = listOf(
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery1.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery2.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery3.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery4.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery5.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery6.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery7.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery8.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery9.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Animals-Gallery10.png"
)

private val volunteersImages = listOf(
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery1.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery2-1.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery3.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery4.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Volunteers-Gallery5.png"
)

private val diamondsImages = listOf(
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery1.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery2.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery3.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery4.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery5.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery6.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery7.png",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/SAID-Diamonds-Gallery8.png"
)

private val horsesImages = listOf(
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/AVALANCHE-SAID.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/AVALANCHE-SAID-3.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/AVALANCHE-SAID-2.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/VET-TREATMENT-HORSES.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/SAID-HORSES-4.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/MEG-AND-AVALANCHE-SAID-scaled.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/SAID-HORSES-3.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2024/06/SAID-HORSES-2.jpg"
)

private val eventsImages = listOf(
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180411_111118-1.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180914_084855-1.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180914_090652.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_20180914_091659-1.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_0461-1.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_1828-1.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_3813-1.jpg",
    "https://animalsindistress.org.za/wp-content/uploads/2023/06/IMG_5325.jpg"
)

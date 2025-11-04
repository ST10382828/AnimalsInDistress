package student.projects.animalsindistress.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import java.io.File
import java.io.FileOutputStream

/**
 * Generates placeholder images for stories when actual photos aren't available.
 * Creates colored rectangles with text labels.
 */
object PlaceholderImageGenerator {
    
    private val imageSpecs = mapOf(
        "luna_before.jpg" to ImageSpec("Luna\nBEFORE", Color.parseColor("#8B4513")),
        "luna_after.jpg" to ImageSpec("Luna\nAFTER", Color.parseColor("#4CAF50")),
        "luna_thumb.jpg" to ImageSpec("Luna", Color.parseColor("#2196F3")),
        "max_before.jpg" to ImageSpec("Max\nBEFORE", Color.parseColor("#795548")),
        "max_after.jpg" to ImageSpec("Max\nAFTER", Color.parseColor("#8BC34A")),
        "bella_rescue.jpg" to ImageSpec("Bella\nBEFORE", Color.parseColor("#607D8B")),
        "bella_therapy.jpg" to ImageSpec("Bella\nAFTER", Color.parseColor("#00BCD4"))
    )
    
    data class ImageSpec(val label: String, val color: Int)
    
    fun generatePlaceholders(context: Context) {
        val mediaDir = File(context.filesDir, "story_placeholders")
        if (!mediaDir.exists()) {
            mediaDir.mkdirs()
        }
        
        imageSpecs.forEach { (filename, spec) ->
            val file = File(mediaDir, filename)
            if (!file.exists() || file.length() == 0L) {
                generateImage(file, spec.label, spec.color)
            }
        }
    }
    
    private fun generateImage(file: File, text: String, bgColor: Int) {
        val width = 800
        val height = 600
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        
        // Background
        canvas.drawColor(bgColor)
        
        // Text
        val paint = Paint().apply {
            color = Color.WHITE
            textSize = 80f
            textAlign = Paint.Align.CENTER
            isAntiAlias = true
            isFakeBoldText = true
        }
        
        // Draw text lines
        val lines = text.split("\n")
        val textBounds = Rect()
        paint.getTextBounds(lines[0], 0, lines[0].length, textBounds)
        val lineHeight = textBounds.height() + 20
        val startY = (height - (lines.size * lineHeight)) / 2f + textBounds.height().toFloat()
        
        lines.forEachIndexed { index, line ->
            canvas.drawText(line, width / 2f, startY + (index * lineHeight).toFloat(), paint)
        }
        
        // Save
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
        }
        bitmap.recycle()
    }
}

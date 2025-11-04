package student.projects.animalsindistress

import android.app.Application
import coil.Coil
import coil.ImageLoader
import coil.disk.DiskCache
import okhttp3.OkHttpClient

class AnimalsApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val imageLoader = ImageLoader.Builder(this)
            .okHttpClient { OkHttpClient.Builder().build() }
            .diskCache(
                DiskCache.Builder()
                    .directory(cacheDir.resolve("coil_cache"))
                    .maxSizePercent(0.02) // 2% of storage
                    .build()
            )
            .build()
        Coil.setImageLoader(imageLoader)
    }
}



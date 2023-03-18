package com.iAKIN.loginapp.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import org.jsoup.Jsoup
import java.net.URL
import java.util.concurrent.Executors

class Site {
    companion object {
        private fun setImage(handler: Handler, imageView: ImageView, image: Bitmap) {
            val h = image.height
            val w = image.width
            val aspRat = w / h.toDouble() // get aspect ratio
            val width = 400
            val height = width / aspRat
            val b = Bitmap.createScaledBitmap(image, width, height.toInt(), false)
            // Only for making changes in UI
            handler.post { imageView.setImageBitmap(b) }
        }

        fun webScrapping(imageView: ImageView, url: String) {
            val extensions = arrayOf(".png", ".jpg", ".jpeg", ".ico", ".gif")
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())

            executor.execute {
                try {
                    val doc = Jsoup.connect(url).get().head()

                    for (item in doc.select("[content]")) {
                        for (extension in extensions) {
                            val content = item.attr("content").trim()
                            if (content.matches("http.+$extension(\\?.*)?".toRegex())) {
                                val ins = URL(content).openStream()
                                val image = BitmapFactory.decodeStream(ins)
                                setImage(handler, imageView, image)
                                return@execute
                            } else if (content.matches(".+$extension(\\?.*)?".toRegex())) {
                                val ins = URL(url + content).openStream()
                                val image = BitmapFactory.decodeStream(ins)
                                setImage(handler, imageView, image)
                                return@execute
                            }
                        }
                    }
                    for (item in doc.select("[href]")) {
                        for (extension in extensions) {
                            val href = item.attr("href").trim()
                            if (href.matches("http.+$extension(\\?.*)?".toRegex())) {
                                val ins = URL(href).openStream()
                                val image = BitmapFactory.decodeStream(ins)
                                setImage(handler, imageView, image)
                                return@execute
                            } else if (href.matches(".+$extension(\\?.*)?".toRegex())) {
                                val ins = URL(url + href).openStream()
                                val image = BitmapFactory.decodeStream(ins)
                                setImage(handler, imageView, image)
                                return@execute
                            }
                        }
                    }
                } catch (_: Exception) {
                }
            }
        }
    }
}
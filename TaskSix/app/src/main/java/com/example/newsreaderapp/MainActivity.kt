package com.example.newsreaderapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.NestedScrollView

class MainActivity : AppCompatActivity() {

    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find Views
        val articleScroll = findViewById<NestedScrollView>(R.id.ArticleScroll)
        val bookmarkBtn = findViewById<ImageButton>(R.id.BookmarkBtn)
        val shareBtn = findViewById<ImageButton>(R.id.ShareBtn)
        val backToTopBtn = findViewById<ImageView>(R.id.BackToTopBtn)

        // Navigation Buttons
        val navIntro = findViewById<Button>(R.id.NavIntro)
        val navPoints = findViewById<Button>(R.id.NavPoints)
        val navAnalysis = findViewById<Button>(R.id.NavAnalysis)
        val navConclusion = findViewById<Button>(R.id.NavConclusion)

        // Section Targets
        val secIntro = findViewById<TextView>(R.id.SecIntro)
        val secPoints = findViewById<TextView>(R.id.SecPoints)
        val secAnalysis = findViewById<TextView>(R.id.SecAnalysis)
        val secConclusion = findViewById<TextView>(R.id.SecConclusion)

        // 1. Quick Navigation Logic
        navIntro.setOnClickListener { scrollToView(articleScroll, secIntro) }
        navPoints.setOnClickListener { scrollToView(articleScroll, secPoints) }
        navAnalysis.setOnClickListener { scrollToView(articleScroll, secAnalysis) }
        navConclusion.setOnClickListener { scrollToView(articleScroll, secConclusion) }

        // 2. Back to Top Logic
        backToTopBtn.setOnClickListener {
            articleScroll.smoothScrollTo(0, 0)
        }

        // 3. Bookmark Toggle
        bookmarkBtn.setOnClickListener {
            isBookmarked = !isBookmarked
            if (isBookmarked) {
                bookmarkBtn.setImageResource(android.R.drawable.btn_star_big_on)
                Toast.makeText(this, "Article Bookmarked", Toast.LENGTH_SHORT).show()
            } else {
                bookmarkBtn.setImageResource(android.R.drawable.btn_star_big_off)
                Toast.makeText(this, "Bookmark Removed", Toast.LENGTH_SHORT).show()
            }
        }

        // 4. Share Logic
        shareBtn.setOnClickListener {
            val title = findViewById<TextView>(R.id.ArticleTitle).text.toString()
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this article!")
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Read this: $title")
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }

    private fun scrollToView(scrollView: NestedScrollView, view: TextView) {
        // We get the 'top' coordinate of the view relative to its parent
        scrollView.smoothScrollTo(0, view.top)
    }
}
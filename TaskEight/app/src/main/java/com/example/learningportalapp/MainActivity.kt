package com.example.learningportalapp

import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var etUrl: EditText
    private lateinit var progressBar: ProgressBar
    private val homeUrl = "https://www.google.com"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        etUrl = findViewById(R.id.etUrl)
        progressBar = findViewById(R.id.progressBar)

        configureWebView()
        setupControls()

        webView.loadUrl(homeUrl)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
        })
    }

    private fun configureWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
        }

        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                progressBar.visibility = View.VISIBLE
                etUrl.setText(url) // Update address bar
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
            }


        }

        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                progressBar.progress = newProgress
            }
        }
    }

    private fun setupControls() {
        findViewById<Button>(R.id.btnGo).setOnClickListener { loadCustomUrl() }
        findViewById<Button>(R.id.btnBack).setOnClickListener {
            if (webView.canGoBack()) webView.goBack()
            else Toast.makeText(this, "No more history", Toast.LENGTH_SHORT).show()
        }
        findViewById<Button>(R.id.btnForward).setOnClickListener { if (webView.canGoForward()) webView.goForward() }
        findViewById<Button>(R.id.btnRefresh).setOnClickListener { webView.reload() }
        findViewById<Button>(R.id.btnHome).setOnClickListener { webView.loadUrl(homeUrl) }

        // Shortcut Logic
        findViewById<Button>(R.id.btnGoogle).setOnClickListener { webView.loadUrl("https://www.google.com") }
        findViewById<Button>(R.id.btnWiki).setOnClickListener { webView.loadUrl("https://www.wikipedia.org") }
        findViewById<Button>(R.id.btnYouTube).setOnClickListener { webView.loadUrl("https://www.youtube.com/") }
        findViewById<Button>(R.id.btnGG).setOnClickListener { webView.loadUrl("https://www.geeksforgeeks.org/") }
        // Add others similarly...
    }

    private fun loadCustomUrl() {
        var url = etUrl.text.toString()
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "https://$url"
        }
        webView.loadUrl(url)
    }
}
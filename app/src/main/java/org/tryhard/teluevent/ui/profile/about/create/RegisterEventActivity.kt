package org.tryhard.teluevent.ui.profile.about.create

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import org.tryhard.teluevent.R

class RegisterEventActivity : AppCompatActivity() {
    lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_event)


        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient()

        // this will load the url of the website
        webView.loadUrl("https://docs.google.com/forms/d/1CcJzJztL8X2Z1TT6OcKcpvhqQ_8vUvIDVLzvBzw4emo/")

        // this will enable the javascript settings
        webView.settings.javaScriptEnabled = true

        // if you want to enable zoom feature
        webView.settings.setSupportZoom(true)
    }
}
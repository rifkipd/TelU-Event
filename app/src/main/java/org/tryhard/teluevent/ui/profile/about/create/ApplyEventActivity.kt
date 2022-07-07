package org.tryhard.teluevent.ui.profile.about.create

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_apply_event.*
import kotlinx.android.synthetic.main.activity_register_event.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.ActivityApplyEventBinding

class ApplyEventActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_event)



        createEventBtn.setOnClickListener {
            val intent = Intent(applicationContext,RegisterEventActivity::class.java)
            startActivity(intent)
        }

    }
}
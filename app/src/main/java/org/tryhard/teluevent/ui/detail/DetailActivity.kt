package org.tryhard.teluevent.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.item_home_horizontal.view.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityDetailBinding
    val storageRef = FirebaseStorage.getInstance().reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val intent = intent
        val extras = intent.extras
        val title = extras?.getString("title")
        val place = extras?.getString("place")
        val date = extras?.getString("date")
        val desc = extras?.getString("description")

        Log.d("TAG","$title")
        Log.d("TAG","$place")
        Log.d("TAG","$date")
        Log.d("TAG","$desc")

        tvTitleEvent.text = title
        tvLocSchedule.text = place
        tvEventDate.text = date
        tvDesc.text = desc

        storageRef.child("uploads/${title}").downloadUrl.addOnSuccessListener {
            Glide.with(baseContext)
                .load(it)
                .into(imageDetail)

        }.addOnFailureListener{
            Log.d("TAG",it.message.toString())
        }


    }





}



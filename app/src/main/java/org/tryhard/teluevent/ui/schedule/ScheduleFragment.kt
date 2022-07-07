package org.tryhard.teluevent.ui.schedule

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.tvLocSchedule
import kotlinx.android.synthetic.main.fragment_schedule.tvTitleSchedule
import kotlinx.android.synthetic.main.item_home_horizontal.view.*
import kotlinx.android.synthetic.main.item_home_vertical.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.event.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ScheduleFragment : Fragment() {




//    private lateinit var binding: FragmentScheduleBinding
    private lateinit var database: DatabaseReference
    private var fAuth = FirebaseAuth.getInstance()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //        calendarView = rootView.findViewById(R.id.calender_view)
//        calendarMonth = rootView.findViewById(R.id.calender_view_title)
//
//
//        calendarMonth.text = dateFormatForMonth.format(calendarView.firstDayOfCurrentMonth)
//        calendarView.setFirstDayOfWeek(Calendar.MONDAY)
//        calendarView.shouldSelectFirstDayOfMonthOnScroll(false)
//
//        listView = rootView.findViewById(R.id.current_events)
//
//        eventTitle = rootView.findViewById(R.id.calender_events_title)


        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val uid = fAuth.currentUser?.uid.toString()
        readData(uid)



    }


    private fun readData(uid:String){
        val storageRef = FirebaseStorage.getInstance().reference
        database  = FirebaseDatabase.getInstance().getReference("SavedEvents")
        database.child(uid).get().addOnSuccessListener {
            if(it.exists()){
                tvNoevent.isVisible = false
                val title = it.child("title").value
                val date = it.child("date").value
                val place = it.child("place").value
                val desc = it.child("desc").value
                val key = it.child("key").value


                tvTitleSchedule.text = title.toString()
                tvLocSchedule.text = place.toString()
                tvDateSchedule.text = date.toString()
//                binding.tvTitleSchedule.text = title.toString()

            }else{
                tvTitleSchedule.isVisible = false
                tvLocSchedule.isVisible = false
                tvDateSchedule.isVisible = false
                imageView4.isVisible = false
                imageloc.isVisible = false
                cardViewSchedule.isVisible = false
                tvNoevent.isVisible


            }

        }.addOnFailureListener{
            Toast.makeText(context,"Failed load data", Toast.LENGTH_SHORT).show()
        }
    }


}
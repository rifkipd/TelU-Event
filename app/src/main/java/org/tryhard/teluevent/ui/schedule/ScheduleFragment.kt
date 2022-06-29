package org.tryhard.teluevent.ui.schedule

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.google.firebase.firestore.FirebaseFirestore
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.event.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class ScheduleFragment : Fragment() {

    var allEvents:ArrayList<Event> = ArrayList<Event>()
    lateinit var listView: ListView
    lateinit var  calendarView: CompactCalendarView
    lateinit var calendarMonth: TextView
    lateinit var eventTitle: TextView
    val dateFormatForMonth = SimpleDateFormat("MMM - yyyy", Locale.getDefault())

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val rootView:View = inflater.inflate(R.layout.fragment_schedule, container, false)

        calendarView = rootView.findViewById(R.id.calender_view)
        calendarMonth = rootView.findViewById(R.id.calender_view_title)


        calendarMonth.text = dateFormatForMonth.format(calendarView.firstDayOfCurrentMonth)
        calendarView.setFirstDayOfWeek(Calendar.MONDAY)
        calendarView.shouldSelectFirstDayOfMonthOnScroll(false)

        listView = rootView.findViewById(R.id.current_events)

        eventTitle = rootView.findViewById(R.id.calender_events_title)
        return rootView
    }




}
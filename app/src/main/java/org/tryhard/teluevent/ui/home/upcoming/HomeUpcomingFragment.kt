package org.tryhard.teluevent.ui.home.upcoming

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home_terbaru.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeVerticalModel
import org.tryhard.teluevent.model.event.Event
import org.tryhard.teluevent.ui.detail.DetailActivity
import org.tryhard.teluevent.ui.home.HomeFragment
import org.tryhard.teluevent.ui.home.terbaru.HomeNewAdapter


class HomeUpcomingFragment : Fragment(),HomeNewAdapter.ItemAdapterCallback {

    private lateinit var dbRef: DatabaseReference
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_terbaru, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventRecyclerView = terbaruRcList
        eventRecyclerView.layoutManager = LinearLayoutManager(activity)
        eventRecyclerView.setHasFixedSize(true)

        eventArrayList = arrayListOf<Event>()
        getEventData()


    }

    private fun getEventData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Event")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (eventSnapshot in snapshot.children){
                        val event = eventSnapshot.getValue(Event::class.java)
                        eventArrayList.add(event!!)
                    }

                    eventRecyclerView.adapter = HomeNewAdapter(eventArrayList,this@HomeUpcomingFragment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onClick(v: View, data: Event) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("title", data.title)
        intent.putExtra("place", data.place)
        intent.putExtra("date", data.date)
        intent.putExtra("description", data.desc)
        startActivity(intent)
        Log.d("TAG","DATA HOME TO DETAIL ACT : $data")
    }


//    override fun onClick(v: View, data: HomeVerticalModel) {
////        Toast.makeText(context,"Percobaan klik item "+ data.title, Toast.LENGTH_SHORT).show()
//        val detail = Intent(activity, DetailActivity::class.java)
//        startActivity(detail)
//    }


//    fun initDataDummy(){
//
//        eventList = ArrayList()
//        eventList.add(HomeVerticalModel("Upcoming Event 1",""))
//        eventList.add(HomeVerticalModel("Upcoming Event 2",""))
//        eventList.add(HomeVerticalModel("Upcoming Event 3",""))
//        eventList.add(HomeVerticalModel("Upcoming Event 4",""))
//        eventList.add(HomeVerticalModel("Upcoming Event 5",""))
//
//    }
}
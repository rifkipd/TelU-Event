package org.tryhard.teluevent.ui.home.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.event.Event
import org.tryhard.teluevent.ui.detail.DetailActivity
import org.tryhard.teluevent.ui.home.SectionPagerAdminAdapter


class HomeAdminFragment : Fragment(),HomeAdminAdapter.ItemAdapterCallback {

   private lateinit var dbRef: DatabaseReference
   private lateinit var eventRecyclerView: RecyclerView
   private lateinit var  eventArrayList: ArrayList<Event>




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_home_admin,container,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventRecyclerView = bigRcList
        eventRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        eventRecyclerView.setHasFixedSize(true)

        eventArrayList = arrayListOf<Event>()
        getEventData()

        val sectionPagerAdapter = SectionPagerAdminAdapter(
            childFragmentManager
        )
        viewPager.adapter = sectionPagerAdapter
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun getEventData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Event")

        dbRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (eventSnapshot in snapshot.children){
                        val event = eventSnapshot.getValue(Event::class.java)
                        event?.key?.toString()
                        eventArrayList.add(event!!)

                    }

                    eventRecyclerView.adapter = HomeAdminAdapter(eventArrayList,this@HomeAdminFragment)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"$error",Toast.LENGTH_SHORT).show()
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

}



package org.tryhard.teluevent.ui.home

import android.os.Bundle
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
import org.tryhard.teluevent.databinding.FragmentHomeBinding
import org.tryhard.teluevent.model.dummy.HomeModel
import org.tryhard.teluevent.model.event.Event


class HomeFragment : Fragment() {

   private lateinit var dbRef: DatabaseReference
   private lateinit var eventRecyclerView: RecyclerView
   private lateinit var  eventArrayList: ArrayList<Event>




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        return inflater.inflate(R.layout.fragment_home,container,false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventRecyclerView = bigRcList
        eventRecyclerView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        eventRecyclerView.setHasFixedSize(true)

        eventArrayList = arrayListOf<Event>()
        getEventData()

        val sectionPagerAdapter = SectionPagerAdapter(
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
                        eventArrayList.add(event!!)
                    }

                    eventRecyclerView.adapter = HomeAdapter(eventArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}



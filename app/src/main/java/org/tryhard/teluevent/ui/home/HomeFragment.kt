package org.tryhard.teluevent.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile_account.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.event.Event
import org.tryhard.teluevent.ui.detail.DetailActivity


class HomeFragment : Fragment(),HomeAdapter.ItemAdapterCallback {
    private lateinit var dbRef: DatabaseReference
    private lateinit var eventRecyclerView: RecyclerView

    private var eventList : ArrayList<Event> = ArrayList()


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
                        event?.key?.toString()
                        eventList.add(event!!)

                    }

                    eventRecyclerView.adapter = HomeAdapter(eventList,this@HomeFragment)

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



    private fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle ) {
        val destinationId = currentDestination?.getAction(resId)?.destinationId.orEmpty()
        currentDestination?.let { node ->
            val currentNode = when (node) {
                is NavGraph -> node
                else -> node.parent
            }
            if (destinationId != 0) {
                currentNode?.findNode(destinationId)?.let { navigate(resId, args) }
            }
        }}


    private fun Int?.orEmpty(default: Int = 0): Int {
        return this ?: default
    }




}



package org.tryhard.teluevent.ui.home.terbaru

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_home_terbaru.*
import kotlinx.android.synthetic.main.fragment_home_terbaru_admin.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentHomeTerbaruBinding
import org.tryhard.teluevent.model.event.Event


class HomeTerbaruAdminFragment : Fragment(){

    private lateinit var binding:FragmentHomeTerbaruBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var eventRecyclerView: RecyclerView
    private lateinit var eventArrayList: ArrayList<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_terbaru_admin, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        eventRecyclerView = terbaruAdminRcList
        eventRecyclerView.layoutManager = LinearLayoutManager(activity)
        eventRecyclerView.setHasFixedSize(true)

        eventArrayList = arrayListOf<Event>()
        getEventData()


    }

    private fun getEventData() {
        dbRef = FirebaseDatabase.getInstance().getReference("Event")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    for (eventSnapshot in snapshot.children){
                        val event = eventSnapshot.getValue(Event::class.java)
                        event?.key = eventSnapshot.key
                        eventArrayList.add(event!!)
                    }

                    eventRecyclerView.adapter = HomeNewAdminAdapter(eventArrayList)

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"Gagal mengambil data ",Toast.LENGTH_SHORT).show()
            }

        })
    }




//    override fun onClick(v: View, data: HomeVerticalModel) {
////        Toast.makeText(context,"Percobaan klik item "+ data.title,Toast.LENGTH_SHORT).show()
//        val detail = Intent(activity,DetailActivity::class.java)
//        startActivity(detail)
//    }


//    fun initDataDummy(){
//
//        eventList = ArrayList()
//        eventList.add(HomeVerticalModel("Event Music",""))
//        eventList.add(HomeVerticalModel("Event Seni",""))
//        eventList.add(HomeVerticalModel("Event Esport",""))
//        eventList.add(HomeVerticalModel("Event Pertemuan Mahasiswa",""))
//        eventList.add(HomeVerticalModel("Informasi Kelulusan",""))
//    }



}



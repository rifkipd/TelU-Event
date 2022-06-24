package org.tryhard.teluevent.ui.home.upcoming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home_terbaru.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.HomeVerticalModel
import org.tryhard.teluevent.ui.home.terbaru.HomeNewAdapter


class HomeUpcomingFragment : Fragment(),HomeNewAdapter.ItemAdapterCallback {

    private var eventList : ArrayList<HomeVerticalModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_terbaru, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataDummy()

        var adapter = HomeNewAdapter(eventList,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        terbaruRcList.layoutManager = layoutManager
        terbaruRcList.adapter = adapter


    }

    override fun onClick(v: View, data: HomeVerticalModel) {
        Toast.makeText(context,"Percobaan klik item "+ data.title, Toast.LENGTH_SHORT).show()
    }


    fun initDataDummy(){

        eventList = ArrayList()
        eventList.add(HomeVerticalModel("Upcoming Event 1",""))
        eventList.add(HomeVerticalModel("Upcoming Event 2",""))
        eventList.add(HomeVerticalModel("Upcoming Event 3",""))
        eventList.add(HomeVerticalModel("Upcoming Event 4",""))
        eventList.add(HomeVerticalModel("Upcoming Event 5",""))

    }
}
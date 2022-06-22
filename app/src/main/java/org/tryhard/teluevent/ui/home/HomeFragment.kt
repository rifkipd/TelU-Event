package org.tryhard.teluevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_home.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.FragmentHomeBinding
import org.tryhard.teluevent.model.dummy.HomeModel

class HomeFragment : Fragment(),HomeAdapter.ItemAdapterCallback {

    private var eventList : ArrayList<HomeModel> = ArrayList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home,container,false)

    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initDataDummy()

        var adapter = HomeAdapter(eventList,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        rcList.layoutManager = layoutManager
        rcList.adapter = adapter

    }

    fun initDataDummy(){
        eventList = ArrayList()
        eventList.add(HomeModel("Event Music",""))
        eventList.add(HomeModel("Event Seni",""))
        eventList.add(HomeModel("Event Esport",""))
    }

//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

    override fun onClick(v: View, data: HomeModel) {
        Toast.makeText(context,"Percobaan klik item "+ data.title, Toast.LENGTH_SHORT).show()
    }
}
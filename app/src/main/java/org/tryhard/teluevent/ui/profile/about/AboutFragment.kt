package org.tryhard.teluevent.ui.profile.about

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_profile_account.*
import org.tryhard.teluevent.R
import org.tryhard.teluevent.model.dummy.ProfileMenuModel
import org.tryhard.teluevent.ui.profile.ProfileMenuAdapter


class AboutFragment : Fragment(),ProfileMenuAdapter.ItemAdapterCallback {
    private var menuArrayList: ArrayList<ProfileMenuModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_account, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initDataDummy()

        var adapter = ProfileMenuAdapter(menuArrayList,this)
        var layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
        profileMenuRcList.layoutManager = layoutManager
        profileMenuRcList.adapter = adapter
    }

    fun initDataDummy(){
        menuArrayList = ArrayList()
        menuArrayList.add(ProfileMenuModel("How To Apply Event"))
        menuArrayList.add(ProfileMenuModel("Help Center"))
        menuArrayList.add(ProfileMenuModel("Privacy & Policy"))
        menuArrayList.add(ProfileMenuModel("Terms & Conditions"))

    }

    override fun onClick(v: View, data: ProfileMenuModel) {
        Toast.makeText(context,"About menu "+data.title+" clicked",Toast.LENGTH_SHORT).show()
    }
}
package org.tryhard.teluevent.ui.schedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.tryhard.teluevent.R


class ScheduleFragment : Fragment() {



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        val root = inflater.inflate(R.layout.fragment_schedule,container,false)
        return root
    }


}
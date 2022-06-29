package org.tryhard.teluevent.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.tryhard.teluevent.ui.home.terbaru.HomeTerbaruAdminFragment
import org.tryhard.teluevent.ui.home.upcoming.HomeUpcomingFragment

class SectionPagerAdminAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Terbaru"
            1 -> "Upcoming"
            else -> ""
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment
        return when (position){
            0-> {
                fragment = HomeTerbaruAdminFragment()
                return fragment
            }
            1-> {
                fragment = HomeUpcomingFragment()
                return fragment
            }
            else ->{
                fragment = HomeTerbaruAdminFragment()
                return fragment
            }
        }
    }

}
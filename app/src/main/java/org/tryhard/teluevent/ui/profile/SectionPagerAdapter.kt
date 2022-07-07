package org.tryhard.teluevent.ui.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.tryhard.teluevent.ui.profile.about.AboutFragment
import org.tryhard.teluevent.ui.profile.account.ProfileAccountFragment

class SectionPagerAdapter(fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getPageTitle(position: Int): CharSequence? {
        return when (position){
            0 -> "Account"
            1 -> "About"
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
                fragment = ProfileAccountFragment()
                return fragment
            }
            1-> {
                fragment = AboutFragment()
                return fragment
            }
            else ->{
                fragment = ProfileAccountFragment()
                return fragment
            }
        }
    }

}
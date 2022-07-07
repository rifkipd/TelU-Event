package org.tryhard.teluevent.ui.auth.admin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.tryhard.teluevent.R
import org.tryhard.teluevent.databinding.ActivityAdminBinding
import org.tryhard.teluevent.databinding.ActivityMainBinding

class AdminActivity : AppCompatActivity() {

    private lateinit var binding:ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main_admin) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(navView,navController)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_homeAdmin, R.id.navigation_schedule , R.id.navigation_profile,R.id.navigation_addEvent))
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)


    }


}
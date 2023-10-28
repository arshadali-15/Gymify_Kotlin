package com.example.primefitness

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btmBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        btmBar.setOnItemSelectedListener {

            if (it.itemId == R.id.nav_home) {
                inflateFragment(HomeFragment.newInstance())
            } else if (it.itemId == R.id.nav_subsEnd) {
                inflateFragment(SubsFragment.newInstance())
            } else if (it.itemId == R.id.nav_profile) {
                inflateFragment(ProfileFragment())
            }
            true;
        }
        btmBar.selectedItemId = R.id.nav_home;
    }
    private fun inflateFragment(newInstance: Fragment) {

        var transaction = supportFragmentManager.beginTransaction();

        transaction.replace(R.id.container, newInstance)

        transaction.commit()
    }

}

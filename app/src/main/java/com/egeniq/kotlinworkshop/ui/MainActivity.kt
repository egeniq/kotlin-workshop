package com.egeniq.kotlinworkshop.ui

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.egeniq.kotlinworkshop.R
import com.egeniq.kotlinworkshop.ui.create.CreateEditFragment

class MainActivity : AppCompatActivity() {

    lateinit var actionButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        actionButton = findViewById(R.id.fab)
        actionButton.setOnClickListener { view ->
            showFragment(CreateEditFragment.instantiate(-1, view.x + view.width / 2, view.y + view.height / 2))
        }

        supportFragmentManager.addOnBackStackChangedListener {
            updateFab()
        }

        showFragment(MainFragment(), false)
    }

    fun showFragment(fragment: Fragment, addToBackStack: Boolean = true) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.main_fragment_container, fragment)
            if (addToBackStack) {
                addToBackStack(null)
            }
        }.commit()
    }

    fun updateFab() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            actionButton.hide()
        } else {
            actionButton.show()
        }
    }
}

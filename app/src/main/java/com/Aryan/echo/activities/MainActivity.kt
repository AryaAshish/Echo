package com.Aryan.echo.activities

import android.annotation.TargetApi
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.Aryan.echo.R
import com.Aryan.echo.adapters.NavigationDrawerAdapter
import com.Aryan.echo.fragments.MainScreenFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import com.Aryan.echo.fragments.SongPlayingFragment

class MainActivity : AppCompatActivity() {
    object Statified {
        var drawerLayout: DrawerLayout? = null
        var notificationManager: NotificationManager? = null
    }

    var navigationDrawerIconsList: ArrayList<String> = arrayListOf()
    var images_for_navdrawer = intArrayOf(R.drawable.navigation_allsongs, R.drawable.navigation_favorites, R.drawable.navigation_settings, R.drawable.navigation_aboutus)
    var trackNotificationBuilder: Notification? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        MainActivity.Statified.drawerLayout = findViewById(R.id.drawer_layout)

        navigationDrawerIconsList.add("All Songs")
        navigationDrawerIconsList.add("Favorites")
        navigationDrawerIconsList.add("Settings")
        navigationDrawerIconsList.add("About Us")

        val toggle = ActionBarDrawerToggle(this@MainActivity, MainActivity.Statified.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        MainActivity.Statified.drawerLayout?.setDrawerListener(toggle)
        toggle.syncState()

        val mainScreenFragment = MainScreenFragment()
        this.supportFragmentManager
                .beginTransaction()
                .add(R.id.details_fragment, mainScreenFragment, "MainScreenFragment")
                .commit()

        val _navigationAdapter = NavigationDrawerAdapter(navigationDrawerIconsList, images_for_navdrawer, this)
        _navigationAdapter.notifyDataSetChanged()

        val navigation_recycler_view = findViewById<RecyclerView>(R.id.navigation_recycler_view)
        navigation_recycler_view.layoutManager = LinearLayoutManager(this)
        navigation_recycler_view.itemAnimator = DefaultItemAnimator()
        navigation_recycler_view.adapter = _navigationAdapter
        navigation_recycler_view.setHasFixedSize(true)

        val intent = Intent(this@MainActivity, MainActivity::class.java)
        val pIntent = PendingIntent.getActivity(this@MainActivity, System.currentTimeMillis().toInt(),
                intent, 0)
            trackNotificationBuilder = Notification.Builder(this)
                    .setContentTitle("A track is playing in background")
                    .setSmallIcon(R.drawable.echo_icon)
                    .setContentIntent(pIntent)
                    .setOngoing(true)
                    .setAutoCancel(true)
                    .build()

        Statified.notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    }

    override fun onStart() {
        try {
            Statified.notificationManager?.cancel(1978)
        }catch(e: Exception){
            e.printStackTrace()
        }
        super.onStart()
    }

    override fun onResume() {
        try {
            Statified.notificationManager?.cancel(1978)
        }catch(e: Exception){
            e.printStackTrace()
        }
        super.onResume()
    }
    override fun onStop() {
        super.onStop()
        try {
            if (SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean) {
                Statified.notificationManager?.notify(1978, trackNotificationBuilder)
            }

        }catch (e: Exception){
            e.printStackTrace()
        }
    }
    override fun onDestroy() {
        try{
            if(SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                Statified.notificationManager?.notify(1978, trackNotificationBuilder)
            }
        }catch(e : Exception) {
            e.printStackTrace()
        }

        super.onDestroy()
    }

}

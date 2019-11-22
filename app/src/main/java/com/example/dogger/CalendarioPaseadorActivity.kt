package com.example.dogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_calendario_paseador.*


class CalendarioPaseadorActivity : AppCompatActivity() {

    lateinit var recyclerView : RecyclerView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario_paseador)

        initRecycler()


        /**snip **/
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)
        //** snip **//
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initRecycler(){
        recyclerView = rv_agenda

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CalendarioPaseadorActivity,
                RecyclerView.HORIZONTAL, false)
            adapter = AdapterAgenda(AgendaDataFactory.getAgenda())
        }

    }
}
package com.example.dogger

import android.os.Build
import android.os.Bundle
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
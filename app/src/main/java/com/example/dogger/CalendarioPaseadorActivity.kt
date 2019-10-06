package com.example.dogger

import android.icu.util.Calendar
import android.icu.util.Calendar.DAY_OF_WEEK
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_calendario_paseador.*
import java.text.DateFormat
import java.time.LocalDate
import java.time.LocalDate.now
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class CalendarioPaseadorActivity : AppCompatActivity() {

    private var paseosDeHoy = arrayOf(
        "Paseo 1",
        "Paseo 2",
        "Paseo 3",
        "Paseo 4",
        "Paseo 5",
        "Paseo 6",
        "Paseo 7",
        "Paseo 8",
        "Paseo 9",
        "Paseo 10"
    )

    private var paseosDeAyer = arrayOf(
        "Paseo 1",
        "Paseo 2",
        "Paseo 3"
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario_paseador)

        val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val calendario = Calendar.getInstance()

        txv_FechaHoy.text = dateFormatter.format(calendario.time)
        calendario.add(DAY_OF_WEEK, 1)
        txv_FechaManiana.text = dateFormatter.format(calendario.time)

        var adapter = ArrayAdapter(this,
            R.layout.list_view_item, paseosDeHoy)

        val listViewPaseosDeHoy: ListView = findViewById(R.id.lsv_PaseosDeHoy)

        listViewPaseosDeHoy.adapter = adapter

        adapter = ArrayAdapter(this,
            R.layout.list_view_item, paseosDeAyer)

        val listViewPaseosDeManiana: ListView = findViewById(R.id.lsv_PaseosDeManiana)

        listViewPaseosDeManiana.adapter = adapter
    }
}
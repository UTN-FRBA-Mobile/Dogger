package com.example.dogger

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class PaseadorInicioActivity : AppCompatActivity(){
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paseador_inicio)

        val adapter = ArrayAdapter(this,
            R.layout.list_view_item, paseosDeHoy)

        val listView: ListView = findViewById(R.id.lsv_paseos_de_hoy)

        listView.setAdapter(adapter)
    }
}
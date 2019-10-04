package com.example.dogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView


class DuenioInicioActivity : AppCompatActivity() {
    var array = arrayOf(
        "9 de julio 10:00",
        "10 de julio 10:00",
        "11 de julio 11:00",
        "12 de julio 09:00",
        "13 de julio 09:00",
        "14 de julio 09:00",
        "15 de julio 09:00",
        "16 de julio 09:00",
        "17 de julio 09:00",
        "18 de julio 09:00",
        "19 de julio 09:00",
        "20 de julio 09:00",
        "21 de julio 09:00",
        "22 de julio 09:00",
        "23 de julio 09:00",
        "24 de julio 09:00",
        "25 de julio 09:00"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_duenio_inicio)

        val adapter = ArrayAdapter(this,
            R.layout.list_view_item, array)

        val listView:ListView = findViewById(R.id.recipe_list_view)
        listView.setAdapter(adapter)
    }
}

package com.example.dogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView


class PosicionPaseadorActivity : AppCompatActivity() {
    var array = arrayOf(
        "Corrientes",
        "Pueyrredon",
        "Jujuy",
        "Juan de Garay",
        "La rioja",
        "Chiclana"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posicion_paseador)

        val adapter = ArrayAdapter(this,
            R.layout.list_view_item, array)

        val listView:ListView = findViewById(R.id.recipe_list_view)
        listView.setAdapter(adapter)
    }
}

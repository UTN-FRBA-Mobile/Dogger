package com.example.dogger

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ListaMascotasActivity : AppCompatActivity() {
    var array = arrayOf(
        "Puchi",
        "Firulias",
        "Pulgoso",
        "Chocolate",
        "Coco",
        "Rambo"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_mascotas)

        val adapter = ArrayAdapter(this,
            R.layout.list_view_item, array)

        val listView: ListView = findViewById(R.id.lista_mascotas)
        listView.setAdapter(adapter)

    }

}
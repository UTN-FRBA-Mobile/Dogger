package com.example.dogger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_lista_mascotas.*

private val PERFIL_REQUEST = 1

class ListaMascotasActivity : AppCompatActivity() {


    lateinit var lista: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var adapterMascotas: AdapterMascotas

    var mascotas = arrayOf(
        Mascota("Puchi", R.drawable.dog_footprint, "Jon", R.drawable.dog_footprint),
        Mascota("Firulais", R.drawable.dog_footprint, "Alvaro", R.drawable.dog_footprint),
        Mascota("Moncho", R.drawable.dog_footprint, "Guille", R.drawable.dog_footprint),
        Mascota("Anga", R.drawable.dog_footprint, "Javier", R.drawable.dog_footprint),
        Mascota("Canuto", R.drawable.dog_footprint, "Carla", R.drawable.dog_footprint),
        Mascota("Boby", R.drawable.dog_footprint, "Tomas", R.drawable.dog_footprint),
        Mascota("Trapo", R.drawable.dog_footprint, "Nico", R.drawable.dog_footprint),
        Mascota("Mugroso", R.drawable.dog_footprint, "Carlos", R.drawable.dog_footprint),
        Mascota("Pulgoso", R.drawable.dog_footprint, "Jon", R.drawable.dog_footprint),
        Mascota("Manaos", R.drawable.dog_footprint, "Laura", R.drawable.dog_footprint)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_mascotas)

        btn_perfil_mascota.setOnClickListener {
            openPerfil()
        }

        lista = findViewById(R.id.lista_mascotas)
        layoutManager = LinearLayoutManager(this)
        adapterMascotas = AdapterMascotas(mascotas)

        lista.layoutManager = layoutManager
        lista.adapter = adapterMascotas

    }

    fun openPerfil() {
        val intent = Intent(this, PerfilMascotaActivity::class.java)
        startActivityForResult(intent, PERFIL_REQUEST)
    }

}
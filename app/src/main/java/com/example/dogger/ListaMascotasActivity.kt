package com.example.dogger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

private val PERFIL_REQUEST = 1

class ListaMascotasActivity : AppCompatActivity() {

    lateinit var lista: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var adapterMascotas: AdapterMascotas

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_mascotas)

        var mascotas = mutableListOf<Mascota>()

        lista = findViewById(R.id.lista_mascotas)
        layoutManager = LinearLayoutManager(this)
        adapterMascotas = AdapterMascotas(mascotas, object: ClickListener{
            override fun onClick(vista: View, position: Int) {
                Toast.makeText(applicationContext, mascotas.get(position).nombre, Toast.LENGTH_SHORT).show()
                openPerfil(mascotas.get(position))
            }
        })

        lista.layoutManager = layoutManager
        lista.adapter = adapterMascotas

        db.collection("mascotas")   //Es asincronico
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("[GET-MASCOTA]", "${document.id} => ${document.data}")
                    mascotas.add(document.toObject(Mascota::class.java))
                }
                adapterMascotas.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.w("[ERROR-MASCOTA]", "Error getting documents.", exception)
            }
    }

    fun openPerfil(mascota: Mascota){
        val intent = Intent(this, PerfilMascotaActivity::class.java)
        intent.putExtra("EXTRA_MASCOTA", mascota)
        startActivityForResult(intent, PERFIL_REQUEST)
    }
}
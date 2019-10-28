package com.example.dogger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_lista_mascotas.*

private val PERFIL_REQUEST = 1

class ListaMascotasActivity : AppCompatActivity() {

    lateinit var lista: RecyclerView
    lateinit var layoutManager:RecyclerView.LayoutManager
    lateinit var adapterMascotas: AdapterMascotas

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_mascotas)

        btn_perfil_mascota.setOnClickListener {
            openPerfil()
        }

        var mascotas = mutableListOf<Mascota>()

        lista = findViewById(R.id.lista_mascotas)
        layoutManager = LinearLayoutManager(this)
        adapterMascotas = AdapterMascotas(mascotas)

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

    fun openPerfil() {
        val intent = Intent(this, PerfilMascotaActivity::class.java)
        startActivityForResult(intent, PERFIL_REQUEST)
    }
}
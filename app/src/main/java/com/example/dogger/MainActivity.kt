package com.example.dogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login.*

private val DUENIO_REQUEST = 1
private val PASEADOR_REQUEST = 2

class MainActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        okButton.setOnClickListener {
            onButtonPressed()
        }

        btn_paseador.setOnClickListener {
            onPaseadorButtonPressed()
        }

        //initialData() //Solo correr sino se tienen datos en Firestore.
    }

    fun onButtonPressed() {
        val intent = Intent(this, DuenioInicioActivity::class.java)
        startActivityForResult(intent, DUENIO_REQUEST)
    }

    fun onPaseadorButtonPressed() {
        val intent = Intent(this, PaseadorInicioActivity::class.java)
        startActivityForResult(intent, PASEADOR_REQUEST)
    }

    fun initialData(){

        val mascotas = arrayListOf(
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

        mascotas.forEach { mascota ->
            db.collection("mascotas")
                .add(mascota)
                .addOnSuccessListener { documentReference -> Log.d("[NEWDOCUMENT]", "DocumentSnapshot added with ID: ${documentReference.id}") }
                .addOnFailureListener { e -> Log.w("[NEWDOCUMENT-ERROR]", "Error adding document", e) }
        }
    }

}


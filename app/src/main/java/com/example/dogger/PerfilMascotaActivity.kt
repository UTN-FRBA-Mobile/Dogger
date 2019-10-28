package com.example.dogger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.perfil_mascota.*

private val ADDRESS_REQUEST = 1

class PerfilMascotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_mascota)

        btn_ver_direccion.setOnClickListener {
            goToMap()
        }
    }

    fun goToMap() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivityForResult(intent, ADDRESS_REQUEST)
    }

}

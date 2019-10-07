package com.example.dogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.login.*

private val DUENIO_REQUEST = 1
private val PASEADOR_REQUEST = 2
private val PERFIL_REQUEST = 3

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        okButton.setOnClickListener {
            onButtonPressed()
        }

        btn_paseador.setOnClickListener {
            onPaseadorButtonPressed()
        }

        btn_perfil_mascota.setOnClickListener {
            openPerfil()
        }
    }

    fun onButtonPressed() {
        val intent = Intent(this, DuenioInicioActivity::class.java)
        startActivityForResult(intent, DUENIO_REQUEST)
    }

    fun onPaseadorButtonPressed() {
        val intent = Intent(this, PaseadorInicioActivity::class.java)
        startActivityForResult(intent, PASEADOR_REQUEST)
    }

    fun openPerfil() {
        val intent = Intent(this, PerfilMascotaActivity::class.java)
        startActivityForResult(intent, PERFIL_REQUEST)
    }

}


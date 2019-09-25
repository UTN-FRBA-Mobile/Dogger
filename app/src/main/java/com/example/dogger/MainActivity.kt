package com.example.dogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import kotlinx.android.synthetic.main.login.*

private val DUENIO_REQUEST = 1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
        okButton.setOnClickListener {
            onButtonPressed()
        }
    }

    fun onButtonPressed() {
        val intent = Intent(this, DuenioInicioActivity::class.java)
        startActivityForResult(intent, DUENIO_REQUEST)
    }

}


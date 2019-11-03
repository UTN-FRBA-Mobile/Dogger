package com.example.dogger

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.perfil_mascota.*

private val ADDRESS_REQUEST = 1

class PerfilMascotaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfil_mascota)

        val mascota = intent.getSerializableExtra("EXTRA_MASCOTA") as Mascota
        txt_nombre.text = mascota.nombre
        rounded_pet_image.setImageResource(mascota.foto)

        txt_nombre_duenio.text = mascota.nombre_duenio
        rounded_owner_image.setImageResource(mascota.foto_duenio)

        btn_ver_direccion.setOnClickListener {
            goToMap(mascota)
        }

        whatsappButton_profile.setOnClickListener {
            openWhatsapp(mascota.celular)
        }
    }

    fun goToMap(mascota:Mascota) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("EXTRA_MASCOTA", mascota)
        startActivityForResult(intent, ADDRESS_REQUEST)
    }

    fun openWhatsapp(toNumber: String) {
        var number = toNumber.replace("+", "").replace(" ", "")

        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        sendIntent.putExtra("jid", "$number@s.whatsapp.net")
        startActivity(sendIntent)
    }
}

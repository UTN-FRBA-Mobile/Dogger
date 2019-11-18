package com.example.dogger

import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_posicion_paseador.*


class PosicionPaseadorActivity : AppCompatActivity() {
    var array = arrayOf(
        "Corrientes",
        "Pueyrredon",
        "Jujuy",
        "Juan de Garay",
        "La rioja",
        "Chiclana",
        "Cordoba",
        "Rivadavia"
    )

    var nroPaseador = "+549 11 3090 8399" // contains spaces.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posicion_paseador)

        val adapter = ArrayAdapter(this,
            R.layout.list_view_item, array)

        val listView:ListView = findViewById(R.id.recipe_list_view)
        listView.setAdapter(adapter)

        fab_whatsapp_btn.setOnClickListener {
            openWhatsapp(nroPaseador)
        }
    }


    fun openWhatsapp(toNumber: String) {
        var number = toNumber.replace("+", "").replace(" ", "")

        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        sendIntent.putExtra("jid", "$number@s.whatsapp.net")
        startActivity(sendIntent)
    }

}

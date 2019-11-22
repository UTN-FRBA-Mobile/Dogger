package com.example.dogger

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_posicion_paseador.*
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log


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

        /**snip **/
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)
        //** snip **//
    }


    fun openWhatsapp(toNumber: String) {
        var number = toNumber.replace("+", "").replace(" ", "")

        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        sendIntent.putExtra("jid", "$number@s.whatsapp.net")
        startActivity(sendIntent)
    }

}

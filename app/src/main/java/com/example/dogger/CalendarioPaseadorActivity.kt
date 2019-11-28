package com.example.dogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_calendario_paseador.*


class CalendarioPaseadorActivity : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : AdapterAgenda
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    private var user : FirebaseUser? = auth.currentUser

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendario_paseador)

        initRecycler()


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

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initRecycler(){
        recyclerView = rv_agenda

        recyclerView.apply {
            layoutManager = LinearLayoutManager(this@CalendarioPaseadorActivity,
                RecyclerView.HORIZONTAL, false)
            adapter = AdapterAgenda(getAgenda())
        }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getAgenda() : List<Dia> {
        val fecha = Calendar.getInstance()
        val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val agenda  = mutableListOf<Dia>()

        for (i in 0..6) {
            fecha.add(Calendar.DAY_OF_WEEK, 1)
            agenda.add(Dia(dateFormatter.format(fecha.time), getPaseos(fecha)))
        }

        return agenda
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getPaseos(fecha : Calendar) : List<Paseo> {
        val dateFormatter = SimpleDateFormat("yyyyMMdd")
        val paseos = mutableListOf<Paseo>()


        db.collection("paseos")   //Es asincronico
            .whereEqualTo("id_paseador", user?.uid)
            .whereEqualTo("fecha_paseo", dateFormatter.format(fecha))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("[GET-PASEOS]", "${document.id} => ${document.data}")
                    paseos.add(document.toObject(Paseo::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("[ERROR-PASEOS]", "Error getting documents.", exception)
            }
        return paseos
    }
}
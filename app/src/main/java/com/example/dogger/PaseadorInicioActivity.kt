package com.example.dogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_paseador_inicio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val LISTA_MASCOTA_REQUEST = 1
private val CALENDARIO_PASEADOR_REQUEST = 2

class PaseadorInicioActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private var paseosDeHoy = arrayOf(
        "Paseo 1",
        "Paseo 2",
        "Paseo 3",
        "Paseo 4",
        "Paseo 5",
        "Paseo 6",
        "Paseo 7",
        "Paseo 8",
        "Paseo 9",
        "Paseo 10"
    )

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_paseador)

        val adapter = ArrayAdapter(
            this,
            R.layout.list_view_item, paseosDeHoy
        )

        val listView: ListView = findViewById(R.id.lsv_paseos_de_hoy)
        listView.setAdapter(adapter)

        btn_mascotas.setOnClickListener {
            onButtonPressed()
        }

        btn_agenda.setOnClickListener {
            onAgendaButtonPressed()
        }

        // Navigation Drawer
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, 0, 0
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)


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

        // Esta pantalla es la primera que ve el Paseador. Por lo tanto en el onCreate podemos hacer el registro
        // del dispositivo para posteriormente realizarle el seguimiento
        // Configuro Retrofit
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.205.109:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create<ApiService>(ApiService::class.java)
        // Obtengo el token del dispositivo
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("[TOKEN-ERROR]", "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Obtengo el token
                val token: String = task.result?.token  as String

                Log.i("[TOKEN]", token)
                // Registro en el backend un dispositivo a seguir
                service.registerDevice("jon", RegisterRequest(token)).enqueue(
                    object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            val registerResponse = response.body()
                            Log.i("[REGISTER-RESPONSE]", registerResponse.toString())
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Log.i("[REGISTER-ERROR]",t.message.toString())
                        }
                    }
                )
            })





    }

    private fun onButtonPressed() {
        val intent = Intent(this, ListaMascotasActivity::class.java)
        startActivityForResult(intent, LISTA_MASCOTA_REQUEST)
    }

    private fun onAgendaButtonPressed() {
        val intent = Intent(this, CalendarioPaseadorActivity::class.java)
        startActivityForResult(intent, CALENDARIO_PASEADOR_REQUEST)
    }

    // Navigation Drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_update -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, UserProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
//                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()

                val broadcastIntent = Intent()
                broadcastIntent.action = "com.package.ACTION_LOGOUT"
                sendBroadcast(broadcastIntent)

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
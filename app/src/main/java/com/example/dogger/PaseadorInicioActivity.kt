package com.example.dogger

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_paseador_inicio.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val LISTA_MASCOTA_REQUEST = 1
private const val CALENDARIO_PASEADOR_REQUEST = 2
private const val LOCATION_REQUEST_CODE = 3

class PaseadorInicioActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var user: FirebaseUser? = auth.currentUser
    private lateinit var database: FirebaseDatabase
    private lateinit var dfReference: DatabaseReference
    private val db = FirebaseFirestore.getInstance()

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_paseador)

        database = FirebaseDatabase.getInstance()
        dfReference = database.getReference()


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
        setNavHeader()

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
                service.registerDevice(this.user?.uid.toString(), RegisterRequest(token)).enqueue(
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

        requestLocationPermissions()
    }

    private fun requestLocationPermissions() {
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            return
        }
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
                val intent = Intent(this, UserProfileActivity::class.java)
                intent.putExtra("View", "READONLY")
                startActivity(intent)
            }
            R.id.nav_update -> {
                val intent = Intent(this, UserProfileActivity::class.java)
                intent.putExtra("View", "UPDATE")
                startActivity(intent)
            }
            R.id.nav_logout -> {
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


    private fun setNavHeader() {
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById(R.id.txtName) as TextView
        val navTypeUser = headerView.findViewById(R.id.txtTypeUser) as TextView

        val userKey = this.user?.uid
        dfReference.child("User").child(userKey!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userName = dataSnapshot.child("name").getValue(String::class.java)
                val userType = dataSnapshot.child("userType").getValue(String::class.java)
                navUsername.text = userName
                navTypeUser.text = userType
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun setSharedPref() {
        val userKey = user?.uid as String
        dfReference.child("User").child(userKey).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sharedPreference = getSharedPreferences("DOGGER-USER",Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                val paseadoruid = userKey
                editor.putString("paseadoruid",paseadoruid)
                editor.commit()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }


    @RequiresApi(Build.VERSION_CODES.N)
    fun getPaseosDeHoy() : List<Paseo> {
        val fecha = Calendar.getInstance()
        val dateFormatter = SimpleDateFormat("yyyyMMdd")
        val paseos = mutableListOf<Paseo>()


        db.collection("paseos")   //Es asincronico
            .whereEqualTo("id_paseador", user?.uid)
            .whereEqualTo("fecha_paseo", dateFormatter.format(fecha))
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("[GET-PASEOS_DE_HOY]", "${document.id} => ${document.data}")
                    paseos.add(document.toObject(Paseo::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.w("[ERROR-PASEOS_DE_HOY]", "Error getting documents.", exception)
            }
        return paseos
    }
}
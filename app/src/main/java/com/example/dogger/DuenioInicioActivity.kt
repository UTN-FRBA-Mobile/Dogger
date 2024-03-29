package com.example.dogger

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_duenio_inicio.*

import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*


private val POSICION_PASEADOR_REQUEST = 1

class DuenioInicioActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var array = arrayOf(
        "9 de julio 10:00",
        "10 de julio 10:00",
        "11 de julio 11:00",
        "12 de julio 09:00",
        "13 de julio 09:00",
        "14 de julio 09:00",
        "15 de julio 09:00",
        "16 de julio 09:00",
        "17 de julio 09:00",
        "18 de julio 09:00",
        "19 de julio 09:00",
        "20 de julio 09:00",
        "21 de julio 09:00",
        "22 de julio 09:00",
        "23 de julio 09:00",
        "24 de julio 09:00",
        "25 de julio 09:00"
    )

    var nroPaseador = "+549 11 3090 8399" // contains spaces.


    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView
    lateinit var service: ApiServiceDuenio

    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var database: FirebaseDatabase
    private lateinit var dfReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_duenio)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.205.109:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        this.service = retrofit.create(ApiServiceDuenio::class.java)

        val adapter = ArrayAdapter(this,
            R.layout.list_view_item, array)

        val listView:ListView = findViewById(R.id.recipe_list_view)
        listView.setAdapter(adapter)

        database = FirebaseDatabase.getInstance()
        dfReference = database.getReference()
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        mapButton.setOnClickListener {
            onButtonPressed()
        }

        fab_btn.setOnClickListener {
            openWhatsapp(nroPaseador)
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
        setSharedPref()

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

    private fun setNavHeader() {
        val navigationView = findViewById(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById(R.id.txtName) as TextView
        val navTypeUser = headerView.findViewById(R.id.txtTypeUser) as TextView

        val userKey = user.uid
        dfReference.child("User").child(userKey).addValueEventListener(object :
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

        val userKey = user.uid
        dfReference.child("User").child(userKey).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val sharedPreference = getSharedPreferences("DOGGER-USER",Context.MODE_PRIVATE)
                val editor = sharedPreference.edit()
                val userName = dataSnapshot.child("name").getValue(String::class.java)
                val nroCel = dataSnapshot.child("nroCel").getValue(String::class.java)
                val userType = dataSnapshot.child("userType").getValue(String::class.java)
                val paseadoruid = dataSnapshot.child("id_paseador").getValue(String::class.java)
                val lat= -34.6037389// TODO: dataSnapshot.child("lat").getValue(Float::class.java)
                val lon = -58.3815704// TODO: dataSnapshot.child("lon").getValue(Float::class.java)
                editor.putString("userName",userName)
                editor.putString("nroCel",nroCel)
                editor.putString("userType",userType)
                editor.putString("paseadoruid",paseadoruid)
                editor.commit()
                editor.putDouble("lat",lat)
                editor.putDouble("lon",lon)
                editor.commit()
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun SharedPreferences.Editor.putDouble(key: String, double: Double) =
        putLong(key, java.lang.Double.doubleToRawLongBits(double))

    fun onButtonPressed() {
        val intent = Intent(this, PosicionPaseadorActivity::class.java)

        val sharedPreference =  getSharedPreferences("DOGGER-USER",Context.MODE_PRIVATE)
        val paseadoruid = sharedPreference.getString("paseadoruid", "default")
        this.service.tracing(FollowRequest(paseadoruid as String,true)).enqueue(
            object : Callback<FollowResponse> {
                override fun onResponse(
                    call: Call<FollowResponse>,
                    response: Response<FollowResponse>
                ) {
                    val FollowResponse = response.body()
                    Log.i("[FOLLOW-RESPONSE]", FollowResponse.toString())

                    startActivityForResult(intent, POSICION_PASEADOR_REQUEST)
                }

                override fun onFailure(call: Call<FollowResponse>, t: Throwable) {
                    Log.i("[FOLLOW-ERROR]",t.message.toString())
                    Toast.makeText(applicationContext, "No se puede seguir al paseador", Toast.LENGTH_SHORT).show()
                }
            }
        )


    }

    fun openWhatsapp(toNumber: String) {
        var number = toNumber.replace("+", "").replace(" ", "")

        val sendIntent = Intent("android.intent.action.MAIN")
        sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
        sendIntent.putExtra("jid", "$number@s.whatsapp.net")
        startActivity(sendIntent)
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
}

package com.example.dogger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login.*

private val DUENIO_REQUEST = 1
private val PASEADOR_REQUEST = 2

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    val db = FirebaseFirestore.getInstance()

    lateinit var toolbar: Toolbar
    lateinit var drawerLayout: DrawerLayout
    lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        okButton.setOnClickListener {
            onButtonPressed()
        }

        btn_paseador.setOnClickListener {
            onPaseadorButtonPressed()
        }

        //initialData() //Solo correr sino se tienen datos en Firestore.

//        // Navigation Drawer
//        toolbar = findViewById(R.id.toolbar)
//        setSupportActionBar(toolbar)
//
//        drawerLayout = findViewById(R.id.drawer_layout)
//        navView = findViewById(R.id.nav_view)
//
//        val toggle = ActionBarDrawerToggle(
//            this, drawerLayout, toolbar, 0, 0
//        )
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//        navView.setNavigationItemSelectedListener(this)
    }

    fun onButtonPressed() {
        val intent = Intent(this, DuenioInicioActivity::class.java)
        startActivityForResult(intent, DUENIO_REQUEST)
    }

    fun onPaseadorButtonPressed() {
        val intent = Intent(this, PaseadorInicioActivity::class.java)
        startActivityForResult(intent, PASEADOR_REQUEST)
    }

    fun initialData(){

        val mascotas = arrayListOf(
            Mascota(
                1,
                "Puchi",
                R.drawable.dog1,
                -34.920345,	-57.969559,
                "Bill",
                R.drawable.owner1,
                "+549 11 6572 4625",
                1
            ),
            Mascota(
                2,
                "Firulais",
                R.drawable.dog2,
                -32.950001,	-60.666668,
                "Einstein",
                R.drawable.owner2,
                "+549 11 61009016",
                1
            ),
            Mascota(
                3,
                "Moncho",
                R.drawable.dog3,
                -31.416668,	-64.183334,
                "Mark",
                R.drawable.owner3,
                "+549 11 61009016",
                1
            ),
            Mascota(
                4,
                "Anga",
                R.drawable.dog4,
                -32.888355,	-68.838844,
                "Carl",
                R.drawable.owner4,
                "+549 11 61009016",
                1
            ),
            Mascota(
                5,
                "Canuto",
                R.drawable.dog5,
                -24.184340,	-65.302177,
                "Rene",
                R.drawable.owner5,
                "+549 11 61009016",
                1
            ),
            Mascota(
                6,
                "Boby",
                R.drawable.dog6,
                -37.320480,	-59.132904,
                "Facundo",
                R.drawable.owner6,
                "+549 11 61009016",
                1
            ),
            Mascota(
                7,
                "Trapo",
                R.drawable.dog7,
                -35.101933,	-60.438538,
                "Steve",
                R.drawable.owner7,
                "+549 11 61009016",
                1
            ),
            Mascota(
                8,
                "Mugroso",
                R.drawable.dog8,
                -41.134258,	-71.308525,
                "Tony",
                R.drawable.owner8,
                "+549 11 61009016",
                1
            ),
            Mascota(
                9,
                "Pulgoso",
                R.drawable.dog9,
                -26.808285,	-65.217590,
                "Stephen",
                R.drawable.owner9,
                "+549 11 61009016",
                1
            ),
            Mascota(
                10,
                "Manaos",
                R.drawable.dog10,
                -34.796581,	-58.276012,
                "Elon",
                R.drawable.owner10,
                "+549 11 61009016",
                1
            )
        )

        mascotas.forEach { mascota ->
            db.collection("mascotas")
                .add(mascota)
                .addOnSuccessListener { documentReference -> Log.d("[NEWDOCUMENT]", "DocumentSnapshot added with ID: ${documentReference.id}") }
                .addOnFailureListener { e -> Log.w("[NEWDOCUMENT-ERROR]", "Error adding document", e) }
        }
    }

    // Navigation Drawer
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_profile -> {
                Toast.makeText(this, "Profile clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_update -> {
                Toast.makeText(this, "Update clicked", Toast.LENGTH_SHORT).show()
            }
            R.id.nav_logout -> {
                Toast.makeText(this, "Sign out clicked", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}


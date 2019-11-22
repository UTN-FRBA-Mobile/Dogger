package com.example.dogger

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_paseador_inicio.*

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
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
package com.example.dogger

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_duenio_inicio.*

import android.content.ComponentName
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_duenio)

        val adapter = ArrayAdapter(this,
            R.layout.list_view_item, array)

        val listView:ListView = findViewById(R.id.recipe_list_view)
        listView.setAdapter(adapter)

        mapButton.setOnClickListener {
            onButtonPressed()
        }

        fab_btn.setOnClickListener {
//            Toast.makeText(this, "Prueba", Toast.LENGTH_LONG).show()
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
    }

    fun onButtonPressed() {
        val intent = Intent(this, PosicionPaseadorActivity::class.java)
        startActivityForResult(intent, POSICION_PASEADOR_REQUEST)
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

package com.example.dogger

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dogger.com.example.dogger.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot


class SeleccionarPaseadorActivity : AppCompatActivity() {

    private lateinit var txtsp: AutoCompleteTextView

    private lateinit var spinner: Spinner

    private lateinit var database: FirebaseDatabase
    private lateinit var dfReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seleccionar_paseador)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dfReference = database.getReference()
        user = auth.currentUser!!

        txtsp = findViewById(R.id.txtsp)
        spinner = findViewById(R.id.spinner)

        val ref = dfReference.child("User")
        ref.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val users = arrayListOf<String>()
                users.add("")
                for (userDataSnapshot in dataSnapshot.children) {
                    val user = userDataSnapshot.getValue(User::class.java)
                    if (user != null) {
                        if("Paseador".equals(user.userType)){
                            users.add(user.name)
                        }
                    }
                }
                val arrayAdapter =
                    ArrayAdapter(applicationContext, android.R.layout.simple_spinner_item, users)
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = arrayAdapter
            }

            override fun onCancelled(databaseError: DatabaseError) =
                Toast.makeText(applicationContext, databaseError.getMessage(), Toast.LENGTH_LONG).show()

        })

        //set spinner
        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtsp.setText(spinner.getSelectedItem().toString())
                txtsp.dismissDropDown()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                txtsp.setText(spinner.getSelectedItem().toString())
                txtsp.dismissDropDown()
            }
        })

    }

    fun save(view: View) {
        var txtPaseador = txtsp.text.toString()
        var isUpdate = false
        if(!TextUtils.isEmpty(txtPaseador)){
            val ref = dfReference.child("User")
            ref.addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var id_paseador = ""
                    for (userDataSnapshot in dataSnapshot.children) {
                        if(!isUpdate){
                            val user = userDataSnapshot.getValue(User::class.java)
                            if (user != null) {
                                if(txtPaseador.equals(user.name)){
                                    var userAuxId= userDataSnapshot.key
                                    if (userAuxId != null) {
                                        id_paseador = userAuxId
                                        isUpdate = true
                                        updateUser(id_paseador)
                                        break
                                    }
                                }
                            }
                        }else{
                            break
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) =
                    Toast.makeText(applicationContext, databaseError.getMessage(), Toast.LENGTH_LONG).show()

            })
        }else{
            Toast.makeText(applicationContext, "Seleccione un Paseador para poder continuar", Toast.LENGTH_LONG).show()
        }
    }

    private fun updateUser(id_paseador: String) {
        var duenio = dfReference.child("User").child(user?.uid)
        duenio.child("id_paseador").setValue(id_paseador)

        var paseador = dfReference.child("User").child(id_paseador)
        paseador.child("duenios").push().setValue(user?.uid)

        startActivity(Intent(this,DuenioInicioActivity::class.java))
    }
}
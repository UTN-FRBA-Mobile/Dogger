package com.example.dogger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.text.TextUtils
import android.view.View
import android.widget.*
import com.google.firebase.auth.*


class UserProfileActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtNroCel:EditText
    private lateinit var txtTypeUser: AutoCompleteTextView
    private lateinit var txtPaseador: EditText
    private lateinit var linearLayout:LinearLayout
    private lateinit var linearLayoutPaseador:LinearLayout
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var spinner: Spinner

    private lateinit var database: FirebaseDatabase
    private lateinit var dfReference: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    private lateinit var view: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        view =intent.getStringExtra("View")

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dfReference = database.getReference()

        txtName =  findViewById(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmail)
        txtNroCel = findViewById(R.id.txtNroCel)
        txtTypeUser = findViewById(R.id.txtTypeUser)
        txtPaseador = findViewById(R.id.txtPaseador)

        user = auth.currentUser!!

        adapter = ArrayAdapter(
            this,
            android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.typeUser)
        )
        txtTypeUser.setAdapter(adapter)

        //set spinner
        spinner = findViewById(R.id.spinner_ip) as Spinner

        spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                txtTypeUser.setText(spinner.getSelectedItem().toString())
                txtTypeUser.dismissDropDown()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                txtTypeUser.setText(spinner.getSelectedItem().toString())
                txtTypeUser.dismissDropDown()
            }
        })

        loadData()

        setFormatView()

    }

    private fun setFormatView() {
        linearLayout = findViewById(R.id.buttonView)
        txtTypeUser.setEnabled(false)
        txtEmail.setEnabled(false)
        when(view){
            "READONLY" -> {
                txtName.setEnabled(false)
                txtNroCel.setEnabled(false)
                linearLayout.setVisibility(View.INVISIBLE)
                linearLayout.setEnabled(false)
                spinner.setVisibility(View.INVISIBLE)
                }
            "UPDATE" -> {
                txtName.setEnabled(true)
                txtNroCel.setEnabled(true)
                linearLayout.setVisibility(View.VISIBLE)
                linearLayout.setEnabled(true)
                spinner.setVisibility(View.VISIBLE)
            }
        }
    }

    fun updateForm(view: View) {
        saveUser()
    }

    private fun loadData(){
        val userKey = user?.uid

        txtEmail.setText(user?.email)

        dfReference.child("User").child(userKey!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                linearLayoutPaseador = findViewById(R.id.linearLayoutPaseador)
                val userName = dataSnapshot.child("name").getValue(String::class.java)
                val nroCel = dataSnapshot.child("nroCel").getValue(String::class.java)
                val userType = dataSnapshot.child("userType").getValue(String::class.java)
                txtPaseador.setEnabled(false)
                if (!"Paseador".equals(userType)){
                    linearLayoutPaseador.setVisibility(View.VISIBLE)
                    linearLayoutPaseador.setEnabled(true)
                    txtPaseador.setVisibility(View.VISIBLE)
                    val id_paseador = dataSnapshot.child("id_paseador").getValue(String::class.java)
                    cargarPaseador(id_paseador)
                }else{
                    linearLayoutPaseador.setVisibility(View.INVISIBLE)
                    linearLayoutPaseador.setEnabled(false)
                    txtPaseador.setVisibility(View.INVISIBLE)
                }
                txtName.setText(userName)
                txtNroCel.setText(nroCel)
                val spinnerPosition = adapter.getPosition(userType)
                spinner.setSelection(spinnerPosition)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun cargarPaseador(idPaseador: String?) {
        dfReference.child("User").child(idPaseador!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                linearLayoutPaseador = findViewById(R.id.linearLayoutPaseador)
                val userName = dataSnapshot.child("name").getValue(String::class.java)
                txtPaseador.setText(userName)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    private fun saveUser() {
        val name:String = txtName.text.toString()
        val userType:String = txtTypeUser.text.toString()
        val nroCel:String = txtNroCel.text.toString()

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(userType)
            && !TextUtils.isEmpty(nroCel)){

            val userDB = database.reference.child("User").child(user?.uid!!)
            try {
                userDB.child("name").setValue(name)
                userDB.child("nroCel").setValue(nroCel)
                userDB.child("userType").setValue(userType)
            } catch (e: Exception) {
                Toast.makeText(this, e.message,Toast.LENGTH_LONG).show()
            }

            action(userType)
        }
    }

    fun cancel(view: View){
        val userKey = user?.uid

        dfReference.child("User").child(userKey!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usertype = dataSnapshot.child("userType").getValue(String::class.java)
                action(usertype.toString())
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }

    fun action(userType: String){
        when (userType) {
            "Dueñio" -> startActivity(Intent(this,DuenioInicioActivity::class.java))
            "Paseador" -> startActivity(Intent(this,PaseadorInicioActivity::class.java))
            else -> { // Note the block
                Toast.makeText(this,"Error:" + userType, Toast.LENGTH_LONG).show()
            }
        }
    }
}
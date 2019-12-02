package com.example.dogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.FirebaseFirestore



class LoginActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    private lateinit var txtUser: EditText
    private lateinit var txtPassword: EditText
    private lateinit var progressBar: ProgressBar


    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)
        progressBar = findViewById(R.id.progressBar)

        auth = FirebaseAuth.getInstance()

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

        //initialData() //Solo correr sino se tienen datos en Firestore.
    }

    fun forgotPassword(view:View){
        startActivity(Intent(this, ForgotPassActivity::class.java))
    }
    fun register(view:View){
        startActivity(Intent(this, RegisterActivity::class.java))
    }
    fun login(view:View){
        loginUser()
    }

    private fun loginUser(){
        val user:String = txtUser.text.toString()
        val password:String = txtPassword.text.toString()
//        val user = "alvaro.arando@gmail.com"
//        val password = "123456"

        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(user, password)
                .addOnCompleteListener(this){
                    task ->
                    if (task.isSuccessful){
                        action()
                    }else{
                        Toast.makeText(this,"Error en la Autenticación", Toast.LENGTH_LONG).show()
                    }
                }

        }
    }

    private fun action(){
        val user: FirebaseUser?=auth.currentUser
        val userKey = user?.uid

        var mDatabase = FirebaseDatabase.getInstance()
        var mDb = mDatabase.getReference()


        mDb.child("User").child(userKey!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usertype = dataSnapshot.child("userType").getValue(String::class.java)
                val idPaseador = dataSnapshot.child("id_paseador").getValue(String::class.java)
                runActivity(usertype, userKey, idPaseador)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    private fun runActivity(usertype: String?, userKey: String?, idPaseador: String?) {

        when (usertype) {
            "Dueñio" -> isFirstSession(userKey, idPaseador)
            "Paseador" -> startActivity(Intent(this,PaseadorInicioActivity::class.java))
            else -> { // Note the block
                Toast.makeText(this,"Error:" + usertype, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isFirstSession(userKey: String?, idPaseador: String?) {
        if (!TextUtils.isEmpty(idPaseador)) {
            startActivity(Intent(this, DuenioInicioActivity::class.java))
        } else {
            startActivity(Intent(this, SeleccionarPaseadorActivity::class.java))
        }
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
}

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
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class LoginActivity : AppCompatActivity() {

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
                runActivity(usertype)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    private fun runActivity(usertype: String?) {

        when (usertype) {
            "Dueñio" -> startActivity(Intent(this,DuenioInicioActivity::class.java))
            "Paseador" -> startActivity(Intent(this,PaseadorInicioActivity::class.java))
            else -> { // Note the block
                Toast.makeText(this,"Error:" + usertype, Toast.LENGTH_LONG).show()
            }
        }
    }
}

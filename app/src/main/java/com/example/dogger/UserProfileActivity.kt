package com.example.dogger

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import android.text.InputType
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.KeyEvent
import android.view.View


class UserProfileActivity : AppCompatActivity() {

    private lateinit var txtName: EditText
    private lateinit var txtEmail: EditText
    private lateinit var txtTypeUser: EditText

    private lateinit var database: FirebaseDatabase
    private lateinit var dfReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private lateinit var view: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        view =intent.getStringExtra("View")

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dfReference = database.getReference()

        txtName =  findViewById<EditText>(R.id.txtName)
        txtEmail = findViewById(R.id.txtEmail)
        txtTypeUser = findViewById(R.id.txtTypeUser)

        loadData()

        setFormatView()

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

    private fun setFormatView() {
        if(view.equals("READONLY")){
            txtName.setEnabled(false)

            txtEmail.setEnabled(false)

            txtTypeUser.setEnabled(false)

        }
    }

    private fun loadData(){
        val user: FirebaseUser?=auth.currentUser
        val userKey = user?.uid

        txtEmail.setText(user?.email)

        dfReference.child("User").child(userKey!!).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val userName = dataSnapshot.child("name").getValue(String::class.java)
                val usertype = dataSnapshot.child("userType").getValue(String::class.java)
                txtName.setText(userName)
                txtTypeUser.setText(usertype)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }

    fun disableInput(editText: EditText) {
        editText.inputType = InputType.TYPE_NULL
        editText.setTextIsSelectable(false)
        editText.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View, keyCode: Int, event: KeyEvent): Boolean {
                return true  // Blocks input from hardware keyboards.
            }
        })
    }

}
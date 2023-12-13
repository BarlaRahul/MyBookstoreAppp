package com.example.mybookstoreapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class SignUpActivity : BaseActivity() {
    var etmail: EditText? = null
    var tpassword: EditText? = null
    var confirm: EditText? = null
    var name: EditText? = null
    var signUpButton: Button? = null
    var haveAccountButton: Button? = null
    var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        supportActionBar!!.hide()
        mAuth = FirebaseAuth.getInstance()


        //sign up button
        signUpButton = findViewById<View>(R.id.signUpButton) as Button
        signUpButton!!.setOnClickListener { view: View? -> createUser() }

        //already have an account button
        haveAccountButton = findViewById<View>(R.id.haveAccountButton) as Button
        etmail = findViewById<View>(R.id.phone) as EditText
        tpassword = findViewById<View>(R.id.password) as EditText
        confirm = findViewById<View>(R.id.confirmPassword) as EditText
        name = findViewById<View>(R.id.username) as EditText
        haveAccountButton!!.setOnClickListener {
            val i = Intent(this@SignUpActivity, SignInActivity::class.java)
            startActivity(i)
        }
    }

    private fun createUser() {
        val email = etmail!!.text.toString()
        val password = tpassword!!.text.toString()
        val username = name!!.text.toString()
        Log.d("fml: ", username)
        val confirm_pass = confirm!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            etmail!!.error = "Email cannot be empty"
            etmail!!.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            tpassword!!.error = "Password cannot be empty"
            tpassword!!.requestFocus()
        } else if (TextUtils.isEmpty(username)) {
            etmail!!.error = "Name cannot be empty"
            etmail!!.requestFocus()
        } else if (TextUtils.isEmpty(confirm_pass)) {
            confirm!!.error = "Passwords do not match"
            confirm!!.requestFocus()
        } else {
            mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(username).build()
                    val user = FirebaseAuth.getInstance().currentUser
                    user!!.updateProfile(profileUpdates).addOnCompleteListener {
                        Toast.makeText(
                            this@SignUpActivity,
                            "User registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@SignUpActivity, SignInActivity::class.java))
                        finish()
                    }
                } else {
                    Toast.makeText(
                        this@SignUpActivity,
                        "Registration Error :" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
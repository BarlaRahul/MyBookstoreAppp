package com.example.mybookstoreapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : BaseActivity() {
    var mAuth: FirebaseAuth? = null
    var Email: EditText? = null
    var Password: EditText? = null
    var signInBtn: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        supportActionBar!!.hide()

//        View v = getLayoutInflater().inflate(R.layout.appbar, findViewById(R.id.myLayout));
//        setMyActionBar(v);
//
        mAuth = FirebaseAuth.getInstance()
        if (mAuth!!.currentUser != null) {
            startActivity(Intent(this@SignInActivity, CategoryActivity::class.java))
            finish()
        }
        Email = findViewById<View>(R.id.UserName) as EditText
        Password = findViewById<View>(R.id.Password) as EditText
        // TextView forgotPass=(TextView) findViewById(R.id.forgotPass);
        signInBtn = findViewById<View>(R.id.signInBtn) as Button
        val SignUP = findViewById<View>(R.id.SignUP) as TextView
        signInBtn!!.setOnClickListener { view: View? -> LoginUser() }
        SignUP.setOnClickListener { signUp() }
    }

    fun signUp() {
        val up = Intent(this, SignUpActivity::class.java)
        startActivity(up)
    }

    private fun LoginUser() {
        val email = Email!!.text.toString()
        val password = Password!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Email!!.error = "Email cannot be empty"
            Email!!.requestFocus()
        } else if (TextUtils.isEmpty(password)) {
            Password!!.error = "Password cannot be empty"
            Password!!.requestFocus()
        } else {
            mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this@SignInActivity, "SignIn Successfully  ", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(Intent(this@SignInActivity, CategoryActivity::class.java))
                } else {
                    Toast.makeText(
                        this@SignInActivity,
                        "SignIn Failed" + task.exception!!.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
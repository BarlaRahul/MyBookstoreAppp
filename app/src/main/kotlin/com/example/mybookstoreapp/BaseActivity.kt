package com.example.mybookstoreapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

open class BaseActivity : AppCompatActivity() {
    var nightMode = false
    var sharedPreference: SharedPreferences? = null
    var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreference = getSharedPreferences("MODE", MODE_PRIVATE)

        // Use safe call operator to handle the nullable sharedPreference
        nightMode = sharedPreference?.getBoolean("night", false) ?: false // light mode is default

        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        val ab = supportActionBar
        ab!!.setDisplayShowCustomEnabled(true)
        ab.setCustomView(R.layout.appbar)
        val v = ab.customView
        val back = v.findViewById<View>(R.id.left_icon) as ImageView
        val cart = v.findViewById<View>(R.id.cart) as ImageView
        val mode = v.findViewById<View>(R.id.mode) as ImageView
        val logout = v.findViewById<View>(R.id.logout) as ImageView

        back.setOnClickListener { finish() }

        cart.setOnClickListener {
            val i = Intent(applicationContext, CartActivity::class.java)
            startActivity(i)
        }

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(applicationContext, "Logged out successfully", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, SignInActivity::class.java))
        }

        mode.setOnClickListener {
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreference?.edit()
                editor?.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreference?.edit()
                editor?.putBoolean("night", true)
            }
            editor?.apply()
        }
    }
}

package com.example.mybookstoreapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button

class CheckoutDoneActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout_done)
        val Browse = findViewById<View>(R.id.Browse) as Button
        Browse.setOnClickListener {
            val Backhome = Intent(this@CheckoutDoneActivity, CategoryActivity::class.java)
            startActivity(Backhome)
        }
    }
}
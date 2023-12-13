package com.example.mybookstoreapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class CategoryActivity : BaseActivity() {
    var BookNovel: ConstraintLayout? = null
    var Category: ConstraintLayout? = null
    var values = IntArray(3)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        val auth = FirebaseAuth.getInstance()
        val welcome = findViewById<View>(R.id.Welcome) as TextView
        val msg = "Welcome, " + auth.currentUser!!.displayName + "!"
        welcome.text = msg
        val Horror = findViewById<View>(R.id.Horror) as Button
        val Fiction = findViewById<View>(R.id.Fiction) as Button
        val Fantasy = findViewById<View>(R.id.Fantasy) as Button
        val Romance = findViewById<View>(R.id.Romance) as Button
        val Crime = findViewById<View>(R.id.Crime) as Button
        val Adventure = findViewById<View>(R.id.Adventure) as Button
        val Realism = findViewById<View>(R.id.Realism) as Button
        val Drama = findViewById<View>(R.id.Drama) as Button

//        ImageButton toCart= (ImageButton) findViewById(R.id.Cart);
//        toCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent toCart=new Intent(CategoryActivity.this, CartActivity.class);
//                startActivity(toCart);
//            }
//        });
        BookNovel = findViewById<View>(R.id.BooksNovels) as ConstraintLayout
        Category = findViewById<View>(R.id.Category) as ConstraintLayout
    }

    fun toBookDisplay(v: View) {
        when (v.id) {
            R.id.Adventure -> values[2] = 0
            R.id.Crime -> values[2] = 1
            R.id.Drama -> values[2] = 2
            R.id.Fantasy -> values[2] = 3
            R.id.Fiction -> values[2] = 4
            R.id.Horror -> values[2] = 5
            R.id.Realism -> values[2] = 6
            R.id.Romance -> values[2] = 7
        }
        val toBD = Intent(this@CategoryActivity, BookDisplayActivity::class.java)
        toBD.putExtra("Values", values)
        startActivity(toBD)
    }

    fun BookNovelView(v: View) {
        when (v.id) {
            R.id.English -> {
                values[0] = 0
                BookNovel!!.visibility = View.VISIBLE
                (findViewById<View>(R.id.Arabic) as ToggleButton).isChecked = false
            }

            R.id.Arabic -> {
                values[0] = 1
                BookNovel!!.visibility = View.VISIBLE
                (findViewById<View>(R.id.English) as ToggleButton).isChecked = false
            }

            else -> BookNovel!!.visibility = View.GONE
        }
    }

    // private ConstraintLayout
    fun CategoryView(v: View) {
        when (v.id) {
            R.id.Books -> {
                values[1] = 0
                Category!!.visibility = View.VISIBLE
                (findViewById<View>(R.id.Novels) as ToggleButton).isChecked = false
            }

            R.id.Novels -> {
                values[1] = 1
                Category!!.visibility = View.VISIBLE
                (findViewById<View>(R.id.Books) as ToggleButton).isChecked = false
            }

            else -> Category!!.visibility = View.GONE
        }
    }
}
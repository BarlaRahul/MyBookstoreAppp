package com.example.mybookstoreapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class BookDetailActivity : BaseActivity() {
    var title: TextView? = null
    var author: TextView? = null
    var price: TextView? = null
    var published: TextView? = null
    var rate: TextView? = null
    var overview: TextView? = null
    var cover: ImageView? = null
    var data: HashMap<String, Any>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        cover = findViewById<View>(R.id.imageView2) as ImageView
        title = findViewById<View>(R.id.textView) as TextView
        author = findViewById<View>(R.id.textView3) as TextView
        price = findViewById<View>(R.id.textView6) as TextView
        overview = findViewById<View>(R.id.textView2) as TextView
        data = intent.getSerializableExtra("data") as HashMap<String, Any>?
        title!!.text = data!!["Name"] as String?
        author!!.text = """
            Author:
            ${data!!["Author"] as String?}
            """.trimIndent()
        val p = (data!!["Price"] as String?)!!.toInt()
        price!!.text = p.toString() + "L.E."
        overview!!.text = data!!["Overview"] as String?
        try {
            val uri = Uri.parse(data!!["Cover"] as String?)
            Glide.with(this).load(uri).into(cover!!)
        } catch (exception: NullPointerException) {
        }
        val ToCart = findViewById<View>(R.id.AddToCart) as Button
        ToCart.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val ref = db.collection("Cart").document("Cart").collection(userId).document(
                (data!!["Name"] as String?)!!
            )
            ref.set(data!!)
                .addOnSuccessListener {
                    val Tocart = Intent(this@BookDetailActivity, CartActivity::class.java)
                    startActivity(Tocart)
                }.addOnFailureListener {
                    Toast.makeText(
                        this@BookDetailActivity,
                        "Error Occured. Couldn't add to cart.",
                        Toast.LENGTH_SHORT
                    )
                }


//                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                            @Override
//                            public void onSuccess(DocumentReference documentReference) {
//                                Intent Tocart= new Intent(BookDetailActivity.this, CartActivity.class);
//                                startActivity(Tocart);
//                            }
//                        })
//                        .addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(BookDetailActivity.this, "Error Occured. Couldn't add to cart.", Toast.LENGTH_SHORT);
//                            }
//                        });
        }
    }
}
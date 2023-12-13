package com.example.mybookstoreapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : BaseActivity() {
    private var rv: RecyclerView? = null
    private var adapter: CartItemAdapter? = null
    private var colRef: CollectionReference? = null
    private var checkout: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        rv = findViewById<View>(R.id.myRecycler) as RecyclerView
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        colRef = db.collection("Cart").document("Cart").collection(userId)
        colRef!!.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val items = ArrayList<HashMap<String, Any>?>()

                //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                for (doc in task.result.documents) {
                    val map = doc.data as HashMap<String, Any>?
                    items.add(map)
                }
                adapter = CartItemAdapter(this@CartActivity, items, true)
                val layout = LinearLayoutManager(this@CartActivity)
                rv!!.layoutManager = layout
                rv!!.adapter = adapter
                val divider = DividerItemDecoration(rv!!.context, layout.orientation)
                divider.setDrawable(getDrawable(R.drawable.gradient_divider)!!)
                rv!!.addItemDecoration(divider)
            }
        }


//        HashMap<String, Object> m = new HashMap<>();
//        m.put("title", "Maybe Someday");
//        m.put("price", "$35.17");
//        items.add(m);
//        m = new HashMap<>();
//        m.put("title", "Spinning Silver");
//        m.put("price", "$35.17");
//        items.add(m);
        checkout = findViewById<View>(R.id.checkout) as Button
        checkout!!.setOnClickListener {
            colRef!!.get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (task.result.documents.isEmpty()) {
                        Toast.makeText(
                            this@CartActivity,
                            "Cart is empty. Put some books in your cart first!",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        val i = Intent(this@CartActivity, CheckoutActivity::class.java)
                        startActivity(i)
                    }
                }
            }
        }
    }
}
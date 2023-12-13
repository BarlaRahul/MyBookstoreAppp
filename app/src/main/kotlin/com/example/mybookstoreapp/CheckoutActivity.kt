package com.example.mybookstoreapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CheckoutActivity : BaseActivity() {
    private var rv: RecyclerView? = null
    private var adapter: CartItemAdapter? = null
    protected var total = 20
    private var cardInfo: LinearLayout? = null
    private var submit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)
        rv = findViewById<View>(R.id.myRecycler) as RecyclerView
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        val colRef = db.collection("Cart").document("Cart").collection(userId)
        colRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val items = ArrayList<HashMap<String, Any>?>()

                //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                for (doc in task.result.documents) {
                    val map = doc.data as HashMap<String, Any>?
                    items.add(map)
                    total = total + (map!!["Price"] as String?)!!.toInt()
                }
                adapter = CartItemAdapter(this@CheckoutActivity, items, false)
                val layout = LinearLayoutManager(this@CheckoutActivity)
                rv!!.layoutManager = layout
                rv!!.adapter = adapter
                val divider = DividerItemDecoration(rv!!.context, layout.orientation)
                divider.setDrawable(getDrawable(R.drawable.gradient_divider)!!)
                rv!!.addItemDecoration(divider)
                (findViewById<View>(R.id.totalFee) as TextView).text = "$total L.E."
            }
        }
        cardInfo = findViewById<View>(R.id.cardInfo) as LinearLayout
        submit = findViewById<View>(R.id.submit) as Button
        submit!!.setOnClickListener { // akid lazem yeb2a fi hena kalam ya5od el input ba2a w y7oto fl database w yfadi el cart kman
            // w tab3an yet2aked en mafi4 baynat na2sa..
            val i = Intent(this@CheckoutActivity, CheckoutDoneActivity::class.java)
            startActivity(i)
        }
    }

    fun editPayOptions(v: View) {
        when (v.id) {
            R.id.cash -> cardInfo!!.visibility = View.GONE
            R.id.payNow -> {
                cardInfo!!.visibility = View.VISIBLE
                cardInfo!!.isFocusable = true
                cardInfo!!.isFocusableInTouchMode = true
                cardInfo!!.requestFocus()
            }
        }
    }
}
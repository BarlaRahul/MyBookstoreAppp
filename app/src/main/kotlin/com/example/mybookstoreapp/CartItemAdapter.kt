package com.example.mybookstoreapp

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CartItemAdapter(
    private val context: Context,
    private val bookList: ArrayList<HashMap<String, Any>?>,
    blue: Boolean
) : RecyclerView.Adapter<CartItemAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView
        var price: TextView
        var cover: ImageView
        var remove: ImageButton

        init {
            title = itemView.findViewById<View>(R.id.title) as TextView
            price = itemView.findViewById<View>(R.id.price) as TextView
            remove = itemView.findViewById<View>(R.id.remove) as ImageButton
            if (priceBlue) {
                price.setTextColor(-0xfa5501)
                remove.visibility = View.VISIBLE
            }
            cover = itemView.findViewById<View>(R.id.cover) as ImageView
        }
    }

    init {
        priceBlue = blue
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val m = bookList[position]
        holder.title.text = m!!["Name"] as String?
        val p = (m["Price"] as String?)!!.toInt()
        holder.price.text = p.toString() + "L.E."
        //holder.cover.setImageResource(m.getCoverId());
        try {
            val uri = Uri.parse(m["Cover"] as String?)
            Glide.with(context).load(uri).into(holder.cover)
        } catch (exception: NullPointerException) {
        }
        holder.remove.setOnClickListener { view ->
            val db = FirebaseFirestore.getInstance()
            val userId = FirebaseAuth.getInstance().currentUser!!.uid
            val ref = db.collection("Cart").document("Cart")
                .collection(userId).document((m["Name"] as String?)!!)
            ref.delete()
            val c = view.context
            (c as Activity).recreate()
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }

    companion object {
        private const var priceBlue = false
    }
}
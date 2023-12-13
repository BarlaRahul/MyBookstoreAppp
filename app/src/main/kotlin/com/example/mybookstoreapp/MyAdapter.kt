package com.example.mybookstoreapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mybookstoreapp.MyAdapter.MyViewHolder

class MyAdapter(var context: Context, var items: ArrayList<HashMap<String, Any>?>) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_design, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val m = items[position]
        try {
            holder.nameView.text = m!!["Name"] as String?
        } catch (exception: NullPointerException) {
            return
        }
        holder.authorView.text = m["Author"] as String?
        //holder.rateView.setText((String)m.get("Rate"));
        val p = (m["Price"] as String?)!!.toInt()
        holder.priceView.text = p.toString() + "L.E."
        try {
            val uri = Uri.parse(m["Cover"] as String?)
            Glide.with(context).load(uri).into(holder.imageView)
        } catch (exception: NullPointerException) {
        }
        holder.v.setOnClickListener { view ->
            val c = view.context
            val i = Intent(c, BookDetailActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            // el data eli bneb3atha l class el book detail
//                i.putExtra("title", ((String) m.get("Title")));
//                i.putExtra("author", ((String) m.get("Author")));
//                i.putExtra("rate", ((String) m.get("Rate")));
//                i.putExtra("price", ((String) m.get("Price")));
//                i.putExtra("overview", ((String) m.get("OVerview")));
            i.putExtra("data", m)
            context.startActivity(i)
            //Toast.makeText(view.getContext(), "you clicked this book", Toast.LENGTH_SHORT).show();
            //da badal ma aktebo fl main
        }
    }

    override fun getItemCount(): Int {
        return items.size // list
    }

    inner class MyViewHolder(var v: View) : RecyclerView.ViewHolder(
        v
    ) {
        var imageView: ImageView
        var nameView: TextView
        var authorView: TextView
        var priceView: TextView
        var rateView: TextView

        init {
            imageView = itemView.findViewById(R.id.imageView)
            nameView = itemView.findViewById(R.id.textViewTitle)
            authorView = itemView.findViewById(R.id.textViewAuthor)
            rateView = itemView.findViewById(R.id.textViewRating)
            priceView = itemView.findViewById(R.id.textViewPrice)
        } //        public void setOnClickListener(View.OnClickListener listener) {
        //         this.setOnClickListener(listener);
        //        }
        //
        //        public Context getContext() {
        //            return this.getContext();
        //        }
    }
}
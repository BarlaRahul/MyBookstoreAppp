package com.example.mybookstoreapp

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore

class BookDisplayActivity : BaseActivity() {
    var recyclerView: RecyclerView? = null
    var Docref: DocumentReference? = null
    var colRef: CollectionReference? = null
    var value: IntArray? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_display)

//        ActionBar ab=getSupportActionBar();
//        ab.setDisplayShowCustomEnabled(true);
//        ab.setCustomView(R.layout.appbar);
        value = intent.getIntArrayExtra("Values")
        recyclerView = findViewById(R.id.recyclerview)
        sequence()
        Log.d("Path: ", colRef!!.path)
        //CollectionReference colRef = FirebaseFirestore.getInstance().collection("English");
        colRef!!.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d("Path: ", "Success")
                val items = ArrayList<HashMap<String, Any>?>()

                //List<DocumentSnapshot> docs = task.getResult().getDocuments();
                for (doc in task.result.documents) {
                    val map = doc.data as HashMap<String, Any>?
                    Log.d("Path: ", map.toString())
                    items.add(map)
                }
             //   recyclerView.setLayoutManager(LinearLayoutManager(this@BookDisplayActivity))
               // recyclerView.setAdapter(MyAdapter(this@BookDisplayActivity, items))
            } else {
                Log.d("verina:", "ffmmmllll")
            }
        }
    }

    fun sequence() {
        when (value!![0]) {
            0 -> Docref = FirebaseFirestore.getInstance().collection("English")
                .document("fgjKQbYSuG67Mt6lUfJo")

            1 -> Docref = FirebaseFirestore.getInstance().collection("Arabic")
                .document("3tGzmSjNIFh6ykzoC1ON")
        }
        when (value!![1]) {
            0 -> Docref = if (value!![0] == 0) {
                Docref!!.collection("Books").document("Xr4rLrzK2x7E7QVWLJ3o")
            } else {
                Docref!!.collection("Books").document("uxr0SZeQt7I0C5nYwGEa")
            }

            1 -> Docref = if (value!![0] == 0) {
                Docref!!.collection("Novels").document("UNbPNxAmFBjSJyT06Mrq")
            } else {
                Docref!!.collection("Novels").document("pPK8BWyklu3PYkeTHUKt")
            }
        }
        when (value!![2]) {
            0 -> colRef = Docref!!.collection("Adventure")
            1 -> colRef = Docref!!.collection("Crime")
            2 -> colRef = Docref!!.collection("Drama")
            3 -> colRef = Docref!!.collection("Fantasy")
            4 -> colRef = Docref!!.collection("Fiction")
            5 -> colRef = Docref!!.collection("Horror")
            6 -> colRef = Docref!!.collection("Realism")
            7 -> colRef = Docref!!.collection("Romance")
        }
    }
}
package com.example.rozrywka

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_show.*

class ShowActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        val fireBase = FirebaseDatabase.getInstance()//instancja patrz List_Activity
        val user = intent.getStringExtra("user")//pobranie usera
        val key = intent.getStringExtra("key")

        val databaseReference =
            fireBase.getReference("Entertainment$user")//otfarcie referencji usera


        if (key != null) {
            databaseReference.child(key).addListenerForSingleValueEvent(object :ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                @SuppressLint("SetTextI18n")
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val en = dataSnapshot.getValue(Entertainment::class.java)
                    if(en!!.category=="Film")
                    {
                        findViewById<ImageView>(R.id.image).setImageResource(R.drawable.film)
                        autorShow.text="Reżyser"
                    }
                    if(en!!.category=="Książka")
                    {
                        findViewById<ImageView>(R.id.image).setImageResource(R.drawable.book)
                        autorShow.text="Autor"
                    }
                    if(en!!.category=="Serial")
                    {
                        findViewById<ImageView>(R.id.image).setImageResource(R.drawable.serial)
                        autorShow.text="Dystrybutor/Reżyser"
                    }
                    if(en!!.category=="Gra")
                    {
                        findViewById<ImageView>(R.id.image).setImageResource(R.drawable.gra)
                        autorShow.text="Platforma"
                    }
                    titleShow.text=titleShow.text.toString()+en!!.title
                    autorShow.text=autorShow.text.toString()+en!!.accessory
                    katgoriaShow.text=katgoriaShow.text.toString()+en!!.category
                    statusShow.text=statusShow.text.toString()+en!!.status
                    gatunekShow.text=gatunekShow.text.toString()+en!!.genre

                }
            })
            btn_edit.setOnClickListener{
                startActivity(Intent(this,AddActivity::class.java).apply {
                    putExtra("user",user)
                    putExtra("key",key)
                })
                finish()
            }

        }
    }
}

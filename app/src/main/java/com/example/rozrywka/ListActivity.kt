package com.example.rozrywka

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_list.*


class ListActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var listofItems:ArrayList<Entertainment>
    lateinit var keys: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        val fireBase= FirebaseDatabase.getInstance()//zainicjonowanie Firebase
        val user=intent.getStringExtra("user")//pobierasz sobie nazwę użytkownika z poprzedniego Activity
        databaseReference=fireBase.getReference("Entertainment$user")//Robisz sobie osobną "Kolumnę" w bazie dla tego użytkownika, lub ją wczytujesz, baza danych jest w formacie JSON
        recyclerView.layoutManager= GridLayoutManager(applicationContext,1)//a to nie wiem co robi
        databaseReference.addValueEventListener(object : ValueEventListener {//a tu nasłuchujesz czy baza powinna być wyświetlona
        override fun onCancelled(p0: DatabaseError) {

        }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                keys= ArrayList()//lista id
                listofItems=ArrayList()//lista obiektów
                for (i in dataSnapshot.children) {//a tu pętla, żeby jechać po dzieciach
                    keys.add(i.key.toString())// Tu se masz to id
                    val newRow=i.getValue(Entertainment::class.java)// A tu sobie jest Obiekt
                    listofItems.add(newRow!!)//A tu sobe dodajesz wiersze do listy
                }
                setupAdapter(listofItems,keys,user)//a tutaj sobie je wypisujesz i przesyłasz id

            }
        })

        action_button.setOnClickListener {
            startActivity(Intent(this,AddActivity::class.java).apply {
                putExtra("user",user)// wysyłasz do Add_Activity UID użytkownika
            })//tu sie otwiera okienko do dodawania nowych obiektów
        }
    }
    private fun setupAdapter(arrayData: ArrayList<Entertainment>,keys:ArrayList<String>,user:String){
        recyclerView.adapter= Adapter(arrayData,this,keys,user)//to se jest adapter :P, tak samo jak w poprzednim semestrze i podobnie jak w C#
    }

}

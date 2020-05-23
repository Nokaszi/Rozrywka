package com.example.rozrywka

import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_add.*
import org.json.JSONObject
import java.net.URL
import java.text.FieldPosition
import java.util.*

class AddActivity : AppCompatActivity() {
    private  lateinit var  databaseReference : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val  fireBase = FirebaseDatabase . getInstance ()//instancja patrz List_Activity
        val user=intent.getStringExtra("user")//pobranie usera
        val key=intent.getStringExtra("key")
        val spinercat=findViewById<Spinner>(R.id.category)
        val spinerstatus=findViewById<Spinner>(R.id.status)
        databaseReference=fireBase.getReference("Entertainment$user")//otfarcie referencji usera


        if(key!=null) {
            databaseReference.child(key).addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val en = dataSnapshot.getValue(Entertainment::class.java)
                    titleAdd.setText(en!!.title)
                    gatunek.setText(en!!.genre)
                    autor.setText(en!!.accessory)
                    if (en!!.category == "Film")
                        spinercat.setSelection(0)
                    if (en!!.category == "Gra")
                        spinercat.setSelection(1)
                    if (en!!.category == "Serial")
                        spinercat.setSelection(2)
                    if (en!!.category == "Książka")
                        spinercat.setSelection(3)
                }
            })
        }

        autor.setOnClickListener{
            Toast.makeText(this,"dasdadas",Toast.LENGTH_SHORT)
        }


        spinercat.onItemSelectedListener=object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                var st= arrayOf("","","")
                if(position==0){
                    st= arrayOf("Obejrzane","W trakcie","Nie obejrzane")
                    autor.hint="Reżyser"
                    abc.isEnabled=true
                   // abc.visibility=View.INVISIBLE
                }
                if(position==1){
                    st= arrayOf("Zagrane","W trakcie","Ukończona")
                    autor.hint="Platforma"
                    abc.isEnabled=false
                   // abc.visibility=View.VISIBLE
                }
                if(position==2){
                    st= arrayOf("Obejrzane","W trakcie","Nie obejrzane")
                    autor.hint="Dystrybutor/Reżyser"
                    abc.isEnabled=false
                   // abc.visibility=View.VISIBLE
                }
                if(position==3){
                    st= arrayOf("Przeczytane","W trakcie","Nie przeczytane")
                    autor.hint="Autor"
                    abc.isEnabled=false
                  //  abc.visibility=View.VISIBLE
                }

                val adapter=ArrayAdapter(this@AddActivity,android.R.layout.simple_spinner_dropdown_item,st)
                spinerstatus.adapter=adapter
            }

        }

         action_button.setOnClickListener{

            val en=Entertainment(titleAdd.text.toString(),spinerstatus.selectedItem.toString(),gatunek.text.toString(),autor.text.toString(),spinercat.selectedItem.toString())//stworzenie obiektu, który ma być wpisany do bazy
            if(key==null)
                databaseReference.child("${Date().time}").setValue(en)//tworzenie nowego dziecka, id to czas, wartości jak w obiekcie en
             else
                databaseReference.child(key).setValue(en)
            startActivity(Intent(this,ListActivity::class.java).apply {
                putExtra("user",user)
            })
             finish()//po zakończeniu przenosi do listy

        }
            findViewById<Button>(R.id.abc).setOnClickListener {
                movieTask().execute()
            }

           /* var result: String?
            val API = "1479be7d"
            val search = titleAdd.text.toString()
            Toast.makeText(this,"Przycisk",Toast.LENGTH_SHORT)
            try {
                result =
                    URL("http://www.omdbapi.com/?t=sith&apikey=1479be7d").readText(
                        Charsets.UTF_8

                    )
                println(result)
            } catch (e: Exception) {
                result = null
                Toast.makeText(this,"Błąd",Toast.LENGTH_SHORT)
            }
            try{
                val jsonObject=JSONObject(result)
                val tite=jsonObject.getString("Title")
                val director=jsonObject.getString("Director")
                val genre=jsonObject.getString("Genre")
                titleAdd.setText(tite)
                autor.setText(director)
                gatunek.setText(genre)

            }
            catch (e:Exception){
                Toast.makeText(this,"Błąd 2",Toast.LENGTH_SHORT)
            }*/
        //}
    }
    inner class movieTask():AsyncTask<String,Void,String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        @SuppressLint("WrongThread")
        override fun doInBackground(vararg p0: String?):String? {
            var result: String?
            val API = "1479be7d"
            val search = findViewById<TextView>(R.id.titleAdd).text.toString()
            try {
                result =
                    URL("http://www.omdbapi.com/?t=$search&apikey=$API").readText(
                        Charsets.UTF_8
                    )
                println(result)
            } catch (e: Exception) {
                result = null
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try{
                val jsonObject=JSONObject(result)
                val tite=jsonObject.getString("Title")
                val director=jsonObject.getString("Director")
                val genre=jsonObject.getString("Genre")
                titleAdd.setText(tite)
                autor.setText(director)
                gatunek.setText(genre)

            }
            catch (e:Exception){
                println("aaaaaaaaaaaaaaaa"+e)
            }
        }
    }
}


package com.example.rozrywka

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class Adapter(private val dataArray: ArrayList<Entertainment>, private val context: Context,
              private val keys:ArrayList<String>, private val user:String): RecyclerView.Adapter<Adapter.MyViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.MyViewHolder {
        val inflater= LayoutInflater.from(parent.context)
        val view =inflater.inflate(R.layout.row_list,parent,false) //stworzenie widoku
        return MyViewHolder(view) //zwrócenie ViewHoldera, co to? Było na c#

    }
    override fun getItemCount(): Int { //nudy
        return dataArray.size
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text=dataArray[holder.adapterPosition].title // wpisanie do wierszy Tytułów
        holder.status.text=dataArray[holder.adapterPosition].status// oraz statusu
        val sadapter=ArrayAdapter<String>(context,R.layout.custome_spinner)
        sadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        holder.spinerS.onItemSelectedListener=object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {
                return
            }
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, positionS: Int, p3: Long) {
                if(positionS==1)context.startActivity(Intent(context,ShowActivity::class.java).apply {
                    putExtra("user",user)
                    putExtra("key",keys[position])
                })
                if(positionS==2){
                    context.startActivity(Intent(context,AddActivity::class.java).apply {
                        putExtra("user",user)
                        putExtra("key",keys[position])
                    })
                }
                if(positionS==3){
                    delete(user,keys[position])
                }
            }

        }
    }
    inner class MyViewHolder(view: View):RecyclerView.ViewHolder(view) { //clasa viewHoldera
        val title = view.findViewById(R.id.title) as TextView
        val status = view.findViewById(R.id.status) as TextView
        val spinerS =view.findViewById<Spinner>(R.id.spinner)
    }

}
fun delete(user:String,key:String){
    val databaseReference= FirebaseDatabase.getInstance().getReference("Entertainment$user").child(key!!)
    databaseReference.removeValue()

}
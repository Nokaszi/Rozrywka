package com.example.rozrywka

import android.annotation.SuppressLint
import android.content.Context
import android.database.DataSetObserver
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.SpinnerAdapter
import android.widget.TextView
import java.util.zip.Inflater

class AdapterSpiner( val context: Context, var listItemsTxt: Array<String>): BaseAdapter() {
    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val mInflater=LayoutInflater.from(context)
        view = mInflater.inflate(R.layout.custome_spinner, null)
        val t1=view.findViewById<TextView>(R.id.spinner_option)
        t1.text = listItemsTxt[position]

        return view
    }
    override fun registerDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getItemViewType(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun getItem(p0: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getViewTypeCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getItemId(p0: Int): Long {
        TODO("Not yet implemented")
    }

    override fun hasStableIds(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDropDownView(position: Int, view: View?, parent: ViewGroup?): View {
        val view: View
        val mInflater=LayoutInflater.from(context)
        view = mInflater.inflate(R.layout.custome_spinner, null)
        val t1=view.findViewById<TextView>(R.id.spinner_option)
        t1.text = listItemsTxt[position]

        return view
    }


    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        TODO("Not yet implemented")
    }

    override fun getCount(): Int {
        return listItemsTxt.size
    }
    inner class ItemRowHolder(row:View?){
        val label:TextView = row?.findViewById((R.id.spinner_option)) as TextView
    }

}
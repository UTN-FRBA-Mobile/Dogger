package com.example.dogger

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recyclerview_agenda.view.*


class AdapterAgenda(private var items: List<Dia>): RecyclerView.Adapter<AdapterAgenda.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =  LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_agenda, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.fecha.text = item.fecha
        val paseosLayoutManager = LinearLayoutManager(holder.paseos.context, RecyclerView.VERTICAL, false)
        paseosLayoutManager.initialPrefetchItemCount = 2
        holder.paseos.apply {
            layoutManager = paseosLayoutManager
            adapter = AdapterPaseos(item.paseos)
            setRecycledViewPool(viewPool)
        }
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        var fecha: TextView = vista.tv_fecha
        var paseos: RecyclerView = vista.lista_paseos

    }
}
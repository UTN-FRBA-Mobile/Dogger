package com.example.dogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_recyclerview_paseos.view.*


class AdapterPaseos(private val items: List<Paseo>): RecyclerView.Adapter<AdapterPaseos.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterPaseos.ViewHolder {
        val vista =  LayoutInflater.from(parent.context).inflate(R.layout.item_recyclerview_paseos, parent, false)
        return ViewHolder(vista)
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onBindViewHolder(holder: AdapterPaseos.ViewHolder, position: Int) {
        val item = items[position]
        holder.nombre.text = item.nombreMascota
        holder.foto.setImageResource(item.fotoMascota)
        holder.direccion.text = item.direccionMascota
        holder.horaRetiro.text = item.horaRetiro
        holder.horaDevolucion.text = item.horaDevolucion
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        var foto: CircleImageView = vista.tv_foto_mascota
        var nombre: TextView = vista.tv_nombre_mascota
        var direccion: TextView = vista.tv_direccion_mascota
        var horaRetiro: TextView = vista.tv_hora_retiro
        var horaDevolucion: TextView = vista.tv_hora_devolucion

    }
}
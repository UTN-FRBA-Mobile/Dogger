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
//        holder.nombre_duenio.text = item.nombre_duenio
//        holder.foto_duenio.setImageResource(item.foto_duenio)

    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        var foto: CircleImageView = vista.rounded_pet_image
        var nombre:TextView = vista.tv_pet_name
//        var foto_duenio:CircleImageView = vista.rounded_pet_owner
//        var nombre_duenio:TextView = vista.tv_pet_owner_name

    }
}
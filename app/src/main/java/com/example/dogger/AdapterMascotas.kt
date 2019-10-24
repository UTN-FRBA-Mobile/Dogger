package com.example.dogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class AdapterMascotas(items: Array<Mascota>): RecyclerView.Adapter<AdapterMascotas.ViewHolder>() {

    var items:Array<Mascota>
    lateinit var viewHolder:ViewHolder

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMascotas.ViewHolder {
        val vista =  LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        viewHolder = ViewHolder(vista)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onBindViewHolder(holder: AdapterMascotas.ViewHolder, position: Int) {
        val item = items.get(position)
        holder.nombre.text = item?.nombre
        holder.foto.setImageResource(item.foto)
        holder.nombre_duenio.text = item?.nombre_duenio
        holder.foto_duenio.setImageResource(item.foto_duenio)
    }

    class ViewHolder(vista: View): RecyclerView.ViewHolder(vista){
        var foto:CircleImageView
        var nombre:TextView
        var foto_duenio:CircleImageView
        var nombre_duenio:TextView

        init {
            foto = vista.rounded_pet_image
            nombre = vista.tv_pet_name
            foto_duenio = vista.rounded_pet_owner
            nombre_duenio = vista.tv_pet_owner_name
        }
    }
}
package com.example.dogger

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_recycler_view.view.*

class AdapterMascotas(items: List<Mascota>, val clickListener: ClickListener): RecyclerView.Adapter<AdapterMascotas.ViewHolder>() {

    var items:List<Mascota>
    lateinit var viewHolder:ViewHolder

    init {
        this.items = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMascotas.ViewHolder {
        val vista =  LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view, parent, false)
        viewHolder = ViewHolder(vista, clickListener)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return this.items.count()
    }

    override fun onBindViewHolder(holder: AdapterMascotas.ViewHolder, position: Int) {
        val item = items.get(position)
        holder.nombre.text = item.nombre
        holder.foto.setImageResource(item.foto)
        holder.nombre_duenio.text = item.nombre_duenio
        holder.foto_duenio.setImageResource(item.foto_duenio)
    }

    class ViewHolder(vista: View, listener: ClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener{
        var foto:CircleImageView
        var nombre:TextView
        var foto_duenio:CircleImageView
        var nombre_duenio:TextView
        var listener: ClickListener

        init {
            foto = vista.rounded_pet_image
            nombre = vista.tv_pet_name
            foto_duenio = vista.rounded_pet_owner
            nombre_duenio = vista.tv_pet_owner_name
            this.listener = listener
            vista.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            this.listener.onClick(v, adapterPosition)
        }
    }
}
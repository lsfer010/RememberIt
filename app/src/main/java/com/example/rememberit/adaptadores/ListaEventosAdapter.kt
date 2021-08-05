package com.example.rememberit.adaptadores

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.rememberit.R
import com.example.rememberit.VerActivity
import com.example.rememberit.adaptadores.ListaEventosAdapter.*
import com.example.rememberit.entidades.Eventos

class ListaEventosAdapter(listaEventos: ArrayList<Eventos>) : RecyclerView.Adapter<EventoViewHolder>() {

    var listaEventos : ArrayList<Eventos>

    init {
        this.listaEventos = listaEventos
    }

    class EventoViewHolder(itemView: View, listaEventos: ArrayList<Eventos>) : RecyclerView.ViewHolder(itemView) {
        val viewTitulo: TextView
        val viewDesc: TextView
        val viewFecha: TextView
        val viewHora: TextView

        init {
            viewTitulo = itemView.findViewById(R.id.viewTituloEvento)
            viewDesc = itemView.findViewById(R.id.viewDescEvento)
            viewFecha = itemView.findViewById(R.id.viewFechaEvento)
            viewHora = itemView.findViewById(R.id.viewHoraEvento)

            //Evento en escucha para cuando hace clic en cualquier de los registros del RecyclerView y con ello abrir la ventana de ver registro
            itemView.setOnClickListener { view ->
                val context = view.getContext()
                val intent = Intent(context, VerActivity::class.java)
                intent.putExtra("ID", listaEventos.get(adapterPosition).getId())
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {
        val view = LayoutInflater.from(parent.getContext()).inflate(
            R.layout.lista_item_evento,
            null,
            false
        )
        return EventoViewHolder(view, listaEventos)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.viewTitulo.setText(listaEventos.get(position).getTitulo())
        holder.viewDesc.setText(listaEventos.get(position).getDesc())
        holder.viewFecha.setText(listaEventos.get(position).getFecha())
        holder.viewHora.setText(listaEventos.get(position).getHora())
    }

    override fun getItemCount(): Int {
        return listaEventos.size
    }
}
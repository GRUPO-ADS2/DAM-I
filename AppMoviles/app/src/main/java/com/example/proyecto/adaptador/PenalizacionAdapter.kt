package com.example.proyecto.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.entidad.Penalizacion
import com.example.proyecto.vistas.ViewPenalizacion

class PenalizacionAdapter(var data:List<Penalizacion>): RecyclerView.Adapter<ViewPenalizacion>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPenalizacion {
        var item= LayoutInflater.from(parent.context).inflate(R.layout.item_penalizacion,parent,false)
        return ViewPenalizacion(item)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: ViewPenalizacion, position: Int) {
        val penalizacion = data[position]
        holder.tvIdPrestamoPen.text = penalizacion.idPenalizacion.toString()
        holder.tvAlumnoPrestamoPena.text = penalizacion.prestamo.solicitud.alumno.nombresApellidos
        holder.tvDescripcionPen.text = penalizacion.descripcion

        // Remove the time part from the date string
        val fechaPrestamoPenalizacion = penalizacion.fechaPenalizacion.substringBefore("T")
        holder.tvFechaPenalizacion.text = fechaPrestamoPenalizacion


    }
}
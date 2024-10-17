package com.example.proyecto.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.entidad.Prestamo
import com.example.proyecto.vistas.ViewPrestamo

class PrestamoAdapter(var data:List<Prestamo>): RecyclerView.Adapter<ViewPrestamo>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPrestamo {
        var item= LayoutInflater.from(parent.context).inflate(R.layout.item_prestamo,parent,false)
        return ViewPrestamo(item)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: ViewPrestamo, position: Int) {
        val prestamo = data[position]
        holder.tvIdPre.text = prestamo.idPrestamo.toString()
        holder.tvAlumnoPre.text = prestamo.solicitud.alumno.nombresApellidos

        // Aquí eliminamos la parte de la hora de la fecha
        val fechaPrestamo = prestamo.fechaPrestamo.substringBefore("T")
        holder.tvFechaPre.text = fechaPrestamo

        val fechaDevReal = prestamo.fechaDevReal?.substringBefore("T") ?: "No devuelto"
        holder.tvFechaDevPre.text = fechaDevReal

        holder.btnActualizarPre.setOnClickListener {
            // Lógica para actualizar el préstamo
        }
    }}
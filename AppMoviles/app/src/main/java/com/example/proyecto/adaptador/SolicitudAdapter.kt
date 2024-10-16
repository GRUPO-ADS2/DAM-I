package com.example.proyecto.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.entidad.Solicitud
import com.example.proyecto.vistas.ViewSolicitud

class SolicitudAdapter(var data:List<Solicitud>): RecyclerView.Adapter<ViewSolicitud>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSolicitud {
        var item= LayoutInflater.from(parent.context).inflate(R.layout.item_solicitud,parent,false)
        return ViewSolicitud(item)
    }
    override fun getItemCount(): Int {
        return data.size
    }
    override fun onBindViewHolder(holder: ViewSolicitud, position: Int) {
        val solicitud = data[position]
        holder.tvIdSoli.text = solicitud.idSolicitud.toString()
        holder.tvNomMaterialSoli.text = solicitud.material.nombre
        holder.tvCantidadSoli.text = solicitud.cantidad.toString()


        holder.tvNomAlumnoSoli.setText(data[position].alumno.nombresApellidos)
        holder.tvIdAlumnoSoli.setText(data[position].alumno.usuarioCodUsuario.toString())


        // Configura el botón Aceptar (si quieres implementar alguna lógica)
        holder.btnAceptarSoli.setOnClickListener {
            // Lógica para aceptar solicitud
        }
}}
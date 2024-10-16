package com.example.proyecto.vistas

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

class ViewSolicitud(item: View): RecyclerView.ViewHolder(item) {
    //declarar atributos
    var tvIdSoli: TextView
    var tvNomMaterialSoli: TextView
    var tvCantidadSoli: TextView
    var tvNomAlumnoSoli: TextView
    var tvIdAlumnoSoli: TextView
    var btnAceptarSoli: Button

    init{
        tvIdSoli=item.findViewById(R.id.tvIdSolicitud)
        tvNomMaterialSoli=item.findViewById(R.id.tvNomMaterialSolicitud)
        tvCantidadSoli=item.findViewById(R.id.tvCantidadSolicitud)
        tvNomAlumnoSoli=item.findViewById(R.id.tvNomAlumnoSolicitud)
        tvIdAlumnoSoli=item.findViewById(R.id.tvIdAlumnoSolicitud)
        btnAceptarSoli=item.findViewById(R.id.btnAceptarSolicitud)
    }
}
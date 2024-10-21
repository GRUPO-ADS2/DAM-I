package com.example.proyecto.vistas

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

class ViewPrestamo(item: View): RecyclerView.ViewHolder(item) {
    //declarar atributos
    var tvIdPre: TextView
    var tvAlumnoPre: TextView
    var tvFechaPre: TextView
    var tvFechaDevPre: TextView
    var btnActualizar: Button

    var tvCodMaterial: TextView
    var tvNombreMaterial: TextView

    init{
        tvIdPre=item.findViewById(R.id.tvIdPrestamo)
        tvAlumnoPre=item.findViewById(R.id.tvAlumnoPrestamo)
        tvFechaPre=item.findViewById(R.id.tvFechaPrestamo)
        tvFechaDevPre=item.findViewById(R.id.tvFechaDevPrestamo)

        btnActualizar=item.findViewById(R.id.btnActualizar)

        tvCodMaterial=item.findViewById(R.id.tvCodMaterial)
        tvNombreMaterial=item.findViewById(R.id.tvNomMaterial)
    }
}
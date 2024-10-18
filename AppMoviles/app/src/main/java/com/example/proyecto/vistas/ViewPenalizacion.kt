package com.example.proyecto.vistas

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

class ViewPenalizacion(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvIdPrestamoPen: TextView = itemView.findViewById(R.id.tvIdPenalizacion)
    val tvAlumnoPrestamoPena: TextView = itemView.findViewById(R.id.tvAlumnoPrestamoPenalizado)
    val tvDescripcionPen: TextView = itemView.findViewById(R.id.tvDescripcionPenalizacion)
    val tvFechaPenalizacion: TextView = itemView.findViewById(R.id.tvFechaPenalizacion)
}

package com.example.proyecto.vistas

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

class ViewPenalizacion(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvIdPrestamoPen: TextView = itemView.findViewById(R.id.tvIdPrestamoPenalizado)
    val tvAlumnoPrestamoPena: TextView = itemView.findViewById(R.id.tvAlumnoPrestamoPenalizado)
    val tvFechaPrestamoPen: TextView = itemView.findViewById(R.id.tvFechaPrestamoPenalizado)
    val tvFechaDevPrestamoPen: TextView = itemView.findViewById(R.id.tvFechaDevPrestamoPenalizado)
    val tvDescripcionPen: TextView = itemView.findViewById(R.id.tvDescripcionPenalizacion)
    val btnPenalizarPrestamo: Button = itemView.findViewById(R.id.btnPenalizarPrestamo)
}

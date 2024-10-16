package com.example.proyecto.vistas

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R

class ViewMaterial(item: View): RecyclerView.ViewHolder(item) {
    //declarar atributos
    var tvCodMaterial: TextView
    var tvNombreMaterial: TextView
    var tvDescripcionMaterial: TextView
    var tvStockMaterial: TextView
    var tvTipoMaterial: TextView
    var btnSumar: Button
    var btnRestar: Button
    var btnElegir: Button

    init{
        tvCodMaterial=item.findViewById(R.id.tvCodMaterial)
        tvNombreMaterial=item.findViewById(R.id.tvNomMaterial)
        tvDescripcionMaterial=item.findViewById(R.id.tvDesMaterial)
        tvStockMaterial=item.findViewById(R.id.tvStock)
        tvTipoMaterial=item.findViewById(R.id.tvTipoMaterial)
        btnSumar=item.findViewById(R.id.btnSumar)
        btnRestar=item.findViewById(R.id.btnRestar)
        btnElegir=item.findViewById(R.id.btnElegir)
    }
}
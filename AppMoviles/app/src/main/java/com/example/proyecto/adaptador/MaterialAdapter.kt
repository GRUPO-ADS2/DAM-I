package com.example.proyecto.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.entidad.Material
import com.example.proyecto.vistas.ViewMaterial

class MaterialAdapter(var data:List<Material>): RecyclerView.Adapter<ViewMaterial>()  {

    // Almacenar la cantidad seleccionada para cada material
    private var selectedQuantities = IntArray(data.size) { 1 } // Inicialmente todas las cantidades en 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewMaterial {
        var item= LayoutInflater.from(parent.context).inflate(R.layout.item_material,parent,false)
        return ViewMaterial(item)
    }
    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewMaterial, position: Int) {
        val material = data[position]
        holder.tvCodMaterial.text = material.codMaterial.toString()
        holder.tvNombreMaterial.text = material.nombre
        holder.tvDescripcionMaterial.text = material.descripcion
        holder.tvStockMaterial.text = material.stock.toString()
        holder.tvTipoMaterial.text = material.tipo

        // Mostrar la cantidad seleccionada
        holder.tvCantidadMaterial.text = selectedQuantities[position].toString()

        // Lógica del botón restar
        holder.btnRestar.setOnClickListener {
            val currentQuantity = selectedQuantities[position]
            if (currentQuantity > 1) {  // Evitar que baje de 1
                selectedQuantities[position] = currentQuantity - 1
                holder.tvCantidadMaterial.text = selectedQuantities[position].toString()
            }
        }

        // Lógica del botón sumar
        holder.btnSumar.setOnClickListener {
            val currentQuantity = selectedQuantities[position]
            if (currentQuantity < material.stock) {  // Evitar que supere el stock disponible
                selectedQuantities[position] = currentQuantity + 1
                holder.tvCantidadMaterial.text = selectedQuantities[position].toString()
            }
        }

        // Lógica del botón Elegir (si necesitas enviar la cantidad seleccionada)
        holder.btnElegir.setOnClickListener {
            val cantidadSeleccionada = selectedQuantities[position]
            // Lógica para enviar o utilizar la cantidad seleccionada
        }
    }
}
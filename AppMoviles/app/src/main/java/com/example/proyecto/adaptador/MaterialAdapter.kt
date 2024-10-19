package com.example.proyecto.adaptador

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.HomeActivity
import com.example.proyecto.R
import com.example.proyecto.dto.SoliDTO
import com.example.proyecto.entidad.Alumno
import com.example.proyecto.entidad.Material
import com.example.proyecto.entidad.Solicitud
import com.example.proyecto.entidad.Usuario
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import com.example.proyecto.utils.SessionManager
import com.example.proyecto.vistas.ViewMaterial

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MaterialAdapter(var data:List<Material>): RecyclerView.Adapter<ViewMaterial>()  {

    // Almacenar la cantidad seleccionada para cada material
    private var selectedQuantities = IntArray(data.size) { 1 } // Inicialmente todas las cantidades en 1
    private val apiService: ApiService = ApiUtils.getAPIService()

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

        // Logic for the "Elegir" button
        holder.btnElegir.setOnClickListener {
            val cantidadSeleccionada = selectedQuantities[position]
            showAlertDialog(holder.itemView.context, material, cantidadSeleccionada)
        }
    }

    private fun showAlertDialog(context: Context, material: Material, cantidadSeleccionada: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirmar Solicitud")
        builder.setMessage("Material: ${material.nombre}\nCantidad: $cantidadSeleccionada\n¿Deseas confirmar esta solicitud?")
        builder.setPositiveButton("Confirmar") { dialog, which ->
            registrarSolicitud(context, material, cantidadSeleccionada)
        }
        builder.setNegativeButton("Cancelar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    private fun registrarSolicitud(context: Context, material: Material, cantidadSeleccionada: Int) {
        val alumno = SessionManager.getUser(context)
        if (alumno == null) {
            Toast.makeText(context, "No se encontró el usuario. Por favor, inicie sesión nuevamente.", Toast.LENGTH_SHORT).show()
            return
        }
        // Verificar el estado del alumno
        if (alumno.estado != "Activo") {
            Toast.makeText(context, "El alumno no está activo para solicitar materiales.", Toast.LENGTH_SHORT).show()
            return
        }

        // Verificar si hay suficiente stock
        if (cantidadSeleccionada > material.stock) {
            Toast.makeText(context, "No hay suficiente stock disponible.", Toast.LENGTH_SHORT).show()
            return
        }

        // Crear una instancia de SoliDTO
        val solicitudDTO = SoliDTO(
            alumnoCodUsuario = alumno.usuarioCodUsuario,
            materialCod = material.codMaterial,
            cantidad = cantidadSeleccionada
        )

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = apiService.registrarSolicitud(solicitudDTO).execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Solicitud registrada con éxito", Toast.LENGTH_SHORT).show()

                        // Redirigir al HomeActivity
                        val intent = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent)

                    } else {
                        Log.e("API_ERROR", "Error al registrar la solicitud: ${response.errorBody()?.string()}")
                        Toast.makeText(context, "Error al registrar la solicitud", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("NETWORK_ERROR", "Error de conexión: ${e.localizedMessage}")
                    Toast.makeText(context, "Error de conexión: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
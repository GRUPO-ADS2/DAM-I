package com.example.proyecto.adaptador

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.dto.PresDTO
import com.example.proyecto.entidad.Solicitud
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import com.example.proyecto.vistas.ViewSolicitud
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SolicitudAdapter(var data: List<Solicitud>) : RecyclerView.Adapter<ViewSolicitud>() {

    private val apiService: ApiService = ApiUtils.getAPIService()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSolicitud {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_solicitud, parent, false)
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
        holder.tvNomAlumnoSoli.text = solicitud.alumno.nombresApellidos
        holder.tvIdAlumnoSoli.text = solicitud.alumno.usuarioCodUsuario.toString()

        holder.btnAceptarSoli.setOnClickListener {
            aceptarSolicitud(holder.itemView.context, solicitud)
        }
    }

    private fun aceptarSolicitud(context: Context, solicitud: Solicitud) {

        val presDTO = PresDTO(
            solicitudId = solicitud.idSolicitud
        )

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.registrarPrestamo(presDTO).execute()
                }
                if (response.isSuccessful) {
                    Toast.makeText(context, "Solicitud aceptada con éxito", Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error al aceptar la solicitud: $errorBody")
                    Toast.makeText(context, "Error al aceptar la solicitud: $errorBody", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("NETWORK_ERROR", "Error de conexión: ${e.localizedMessage}")
                e.printStackTrace()  // Print the full stack trace for more details
                Toast.makeText(context, "Error de conexión: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
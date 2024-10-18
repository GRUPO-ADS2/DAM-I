package com.example.proyecto.adaptador

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.dto.PenaDTO
import com.example.proyecto.entidad.Prestamo
import com.example.proyecto.vistas.ViewPrestamo
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.text.SimpleDateFormat

class PrestamoAdapter(var data: List<Prestamo>) : RecyclerView.Adapter<ViewPrestamo>() {

    private val apiService: ApiService = ApiUtils.getAPIService()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPrestamo {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.item_prestamo, parent, false)
        return ViewPrestamo(item)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewPrestamo, position: Int) {
        val prestamo = data[position]
        holder.tvIdPre.text = prestamo.idPrestamo.toString()
        holder.tvAlumnoPre.text = prestamo.solicitud.alumno.nombresApellidos
        holder.tvCodMaterial.text = prestamo.solicitud.material.codMaterial.toString()
        holder.tvNombreMaterial.text = prestamo.solicitud.material.nombre

        val fechaPrestamo = prestamo.fechaPrestamo.substringBefore("T")
        holder.tvFechaPre.text = fechaPrestamo

        if (prestamo.estado == "En Curso") {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val prestamoDate = dateFormat.parse(fechaPrestamo)

            val calendar = Calendar.getInstance()
            calendar.time = prestamoDate
            calendar.add(Calendar.DAY_OF_YEAR, 7)
            val fechaDevReal = dateFormat.format(calendar.time)

            holder.tvFechaDevPre.text = fechaDevReal

            holder.btnExtenderPlazo.visibility = View.VISIBLE
            holder.btnExtenderPlazo.setOnClickListener {
                extenderPlazo(prestamo.idPrestamo)
            }

            holder.btnPenalizar.visibility = View.VISIBLE
            holder.btnPenalizar.setOnClickListener {
                penalizarPrestamo(prestamo.idPrestamo)
            }
        } else {
            holder.btnExtenderPlazo.visibility = View.GONE
            holder.btnPenalizar.visibility = View.VISIBLE
        }
    }

    private fun extenderPlazo(idPrestamo: Int) {
        apiService.actualizarPrestamo(idPrestamo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                } else {
                    // Manejar error
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Manejar error de conexión
            }
        })
    }

    private fun penalizarPrestamo(idPrestamo: Int) {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val currentDate = dateFormat.format(calendar.time)

        val penalizacion = PenaDTO(
            prestamoId = idPrestamo,
            descripcion = "Penalización por retraso",
        )

        apiService.registrarPenalizacion(penalizacion).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Penalización exitosa
                } else {
                    // Manejar error
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Manejar error de conexión
            }
        })
    }
}
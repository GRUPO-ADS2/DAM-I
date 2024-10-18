package com.example.proyecto.adaptador

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.R
import com.example.proyecto.entidad.Alumno
import com.example.proyecto.entidad.Material
import com.example.proyecto.entidad.Penalizacion
import com.example.proyecto.entidad.Prestamo
import com.example.proyecto.entidad.Solicitud
import com.example.proyecto.entidad.Usuario
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

        // Aquí eliminamos la parte de la hora de la fecha
        val fechaPrestamo = prestamo.fechaPrestamo.substringBefore("T")
        holder.tvFechaPre.text = fechaPrestamo

        // Cambiar el texto y la funcionalidad del botón según el estado del préstamo
        if (prestamo.estado == "En Curso") {
            // Parsear la fecha de prestamo
            val dateFormat = SimpleDateFormat("yyyy-MM-dd")
            val prestamoDate = dateFormat.parse(fechaPrestamo)

            // Sumar 7 días a la fecha de prestamo
            val calendar = Calendar.getInstance()
            calendar.time = prestamoDate
            calendar.add(Calendar.DAY_OF_YEAR, 7)
            val fechaDevReal = dateFormat.format(calendar.time)

            holder.tvFechaDevPre.text = fechaDevReal

            holder.btnActualizarPre.text = "Extender Plazo"
            holder.btnActualizarPre.setOnClickListener {
                // Lógica para extender el plazo del préstamo
                extenderPlazo(prestamo.idPrestamo)
            }
        } else if (prestamo.estado == "Penalizado") {
            val fechaDevReal = prestamo.fechaDevReal?.substringBefore("T") ?: "No devuelto"
            holder.tvFechaDevPre.text = fechaDevReal

            holder.btnActualizarPre.text = "Penalizar"
            holder.btnActualizarPre.setOnClickListener {
                // Lógica para penalizar el préstamo
                penalizarPrestamo(prestamo.idPrestamo)
            }
        }
    }

    private fun extenderPlazo(idPrestamo: Int) {
        // Lógica para extender el plazo del préstamo
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
        // Obtener la fecha actual usando Calendar
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd" )
        val currentDate = dateFormat.format(calendar.time)

        // Crear un objeto Penalizacion
        val penalizacion = Penalizacion(
            idPenalizacion = 0, // Asumiendo que el ID es autogenerado
            fechaPenalizacion = currentDate,
            descripcion = "Penalización por retraso",
            prestamo = Prestamo(
                idPrestamo = idPrestamo,
                fechaPrestamo = "", // Reemplazar con la fecha real si es necesario
                estado = "Penalizado",
                fechaDevReal = null,
                solicitud = Solicitud(
                    idSolicitud = 0, // Reemplazar con datos reales
                    cantidad = 0, // Reemplazar con datos reales
                    estado = "",
                    material = Material(0, "", "", 0, ""),
                    alumno = Alumno(0, "", "", 0, Usuario(0, "", ""))
                )
            )
        )

        // Llamar a la API para registrar la penalización
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
package com.example.proyecto.adaptador

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.HomeActivity
import com.example.proyecto.R
import com.example.proyecto.dto.PenaDTO
import com.example.proyecto.entidad.Prestamo
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import com.example.proyecto.utils.SessionManager
import com.example.proyecto.vistas.ViewPrestamo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Calendar

class PrestamoAdapter(private val context: Context, var data: List<Prestamo>) : RecyclerView.Adapter<ViewPrestamo>() {

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

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val prestamoDate = dateFormat.parse(fechaPrestamo)

        val calendar = Calendar.getInstance()
        calendar.time = prestamoDate
        calendar.add(Calendar.DAY_OF_YEAR, 7)
        val fechaDevReal = dateFormat.format(calendar.time)

        holder.tvFechaDevPre.text = fechaDevReal

        if (prestamo.estado == "En Curso") {
            holder.btnActualizar.visibility = View.VISIBLE
            holder.btnActualizar.setOnClickListener {
                showActualizarDialog(prestamo.idPrestamo)
            }
        } else {
            val alumno = SessionManager.getUser(context)
            if (alumno != null && alumno.usuarioCodUsuario == 3 && alumno.usuario.role == "admin") {
                holder.btnActualizar.visibility = View.VISIBLE
                holder.btnActualizar.setOnClickListener {
                    showActualizarDialog(prestamo.idPrestamo)
                }
            } else
            holder.btnActualizar.visibility = View.GONE
        }
    }

    private fun showActualizarDialog(idPrestamo: Int) {
        val alumno = SessionManager.getUser(context)
        val options =
            if (alumno != null && alumno.usuarioCodUsuario == 3 && alumno.usuario.role == "admin") {
                arrayOf("Extender Plazo", "Marcar como Devuelto", "Penalizar")
            } else {
                arrayOf("Extender Plazo")
        }

        val builder = AlertDialog.Builder(context)
        builder.setTitle("Actualizar Préstamo")
        builder.setItems(options) { dialog, which ->
            when (options[which]) {
                "Extender Plazo" -> showConfirmDialog("Extender Plazo", "¿Estás seguro de que deseas extender el plazo?", { extenderPlazo(idPrestamo) })
                "Marcar como Devuelto" -> showConfirmDialog("Registrar Devolución", "¿Estás seguro de que deseas registrar la devolución?", { registrarDevolucion(idPrestamo) })
                "Penalizar" -> penalizarPrestamo(idPrestamo)

            }
        }
        builder.show()
    }

    private fun showConfirmDialog(title: String, message: String, onConfirm: () -> Unit) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Sí") { dialog, _ ->
            onConfirm()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun extenderPlazo(idPrestamo: Int) {
        apiService.actualizarPrestamo(idPrestamo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Actualización exitosa
                    Toast.makeText(context, "Plazo extendido con éxito", Toast.LENGTH_SHORT).show()
                    // Redirigir al HomeActivity
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                } else {
                    // Manejar error
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error al extender el plazo: $errorBody")
                    Toast.makeText(context, "Error al extender el plazo: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                // Manejar error de conexión
                Log.e("NETWORK_ERROR", "Error de conexión: ${t.localizedMessage}")
                t.printStackTrace()  // Imprimir el stack trace para más detalles
                Toast.makeText(context, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun registrarDevolucion(idPrestamo: Int) {
        apiService.registrarDevolucion(idPrestamo).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Devolución registrada con éxito", Toast.LENGTH_SHORT).show()
                    val intent = Intent(context, HomeActivity::class.java)
                    context.startActivity(intent)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("API_ERROR", "Error al registrar la devolución: $errorBody")
                    Toast.makeText(context, "Error al registrar la devolución: $errorBody", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("NETWORK_ERROR", "Error de conexión: ${t.localizedMessage}")
                t.printStackTrace()
                Toast.makeText(context, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun penalizarPrestamo(idPrestamo: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Penalizar Préstamo")

        // Set up the input
        val input = EditText(context)
        input.hint = "Descripción de la penalización"
        builder.setView(input)

        // Set up the buttons
        builder.setPositiveButton("Penalizar") { dialog, _ ->
            val descripcion = input.text.toString()
            if (descripcion.isNotEmpty()) {
                val calendar = Calendar.getInstance()
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val currentDate = dateFormat.format(calendar.time)

                val penalizacion = PenaDTO(
                    prestamoId = idPrestamo,
                    descripcion = descripcion,
                )

                apiService.registrarPenalizacion(penalizacion).enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            // Penalización exitosa
                            Toast.makeText(context, "Penalización registrada con éxito", Toast.LENGTH_SHORT).show()
                        } else {
                            // Manejar error
                            val errorBody = response.errorBody()?.string()
                            Log.e("API_ERROR", "Error al registrar la penalización: $errorBody")
                            Toast.makeText(context, "Error al registrar la penalización: $errorBody", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        // Manejar error de conexión
                        Log.e("NETWORK_ERROR", "Error de conexión: ${t.localizedMessage}")
                        t.printStackTrace()  // Imprimir el stack trace para más detalles
                        Toast.makeText(context, "Error de conexión: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(context, "La descripción no puede estar vacía", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }

        builder.show()
    }
}
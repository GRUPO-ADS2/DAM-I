package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adaptador.PrestamoAdapter
import com.example.proyecto.adaptador.PenalizacionAdapter
import com.example.proyecto.entidad.Penalizacion
import com.example.proyecto.entidad.Prestamo
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SeguimientoActivity : AppCompatActivity() {

    private lateinit var buttonVol:Button
    private lateinit var rvVigentes: RecyclerView
    private lateinit var rvNoVigentes: RecyclerView

    private lateinit var apiServices: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seguimiento)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonVol=findViewById(R.id.buttonVol)
        rvVigentes=findViewById(R.id.rvVigentes)
        rvNoVigentes=findViewById(R.id.rvNoVigentes)

        apiServices = ApiUtils.getAPIService()

        listado()

        buttonVol.setOnClickListener {
            var intent= Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

    }
    fun showAlert(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("SISTEMA")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun listado() {
        // Llamada para listar los préstamos
        apiServices.findAllPrestamos().enqueue(object : Callback<List<Prestamo>> {
            override fun onResponse(call: Call<List<Prestamo>>, response: Response<List<Prestamo>>) {
                val prestamo = response.body()
                if (prestamo != null) {
                    val adaptador = PrestamoAdapter(prestamo)
                    rvVigentes.layoutManager = LinearLayoutManager(this@SeguimientoActivity)
                    rvVigentes.adapter = adaptador
                } else {
                    showAlert("No se encontraron préstamos.")
                }
            }

            override fun onFailure(call: Call<List<Prestamo>>, t: Throwable) {
                showAlert("Error de conexión al cargar préstamos: ${t.localizedMessage}")
            }
        })

        // Llamada para listar las penalizaciones
        apiServices.findAllPenalizaciones().enqueue(object : Callback<List<Penalizacion>> {
            override fun onResponse(call: Call<List<Penalizacion>>, response: Response<List<Penalizacion>>) {
                val penalizaciones = response.body()
                if (penalizaciones != null) {
                    val adaptadorPenalizacion = PenalizacionAdapter(penalizaciones)
                    rvNoVigentes.layoutManager = LinearLayoutManager(this@SeguimientoActivity)
                    rvNoVigentes.adapter = adaptadorPenalizacion
                } else {
                    showAlert("No se encontraron penalizaciones.")
                }
            }

            override fun onFailure(call: Call<List<Penalizacion>>, t: Throwable) {
                showAlert("Error de conexión al cargar penalizaciones: ${t.localizedMessage}")
            }
        })
    }
}
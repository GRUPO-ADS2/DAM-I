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
import com.example.proyecto.adaptador.SolicitudAdapter
import com.example.proyecto.entidad.Solicitud
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrarActivity : AppCompatActivity() {

    private lateinit var buttonVol: Button
    private lateinit var rvPrestamos: RecyclerView

    private lateinit var apiServices: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonVol=findViewById(R.id.buttonVol)

        rvPrestamos = findViewById(R.id.rvPrestamos)

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
        apiServices.findAllSolicitudes().enqueue(object : Callback<List<Solicitud>> {
            override fun onResponse(call: Call<List<Solicitud>>, response: Response<List<Solicitud>>) {
                val solicitudes = response.body()
                if (solicitudes != null) {
                    val adaptador = SolicitudAdapter(solicitudes)
                    rvPrestamos.layoutManager = LinearLayoutManager(this@RegistrarActivity)
                    rvPrestamos.adapter = adaptador
                } else {
                    showAlert("No se encontraron solicitudes.")
                }
            }

            override fun onFailure(call: Call<List<Solicitud>>, t: Throwable) {
                showAlert("Error de conexión: ${t.localizedMessage}")
            }
        })
    }

}
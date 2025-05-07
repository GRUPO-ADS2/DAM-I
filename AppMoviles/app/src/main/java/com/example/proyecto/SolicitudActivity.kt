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
import com.example.proyecto.adaptador.MaterialAdapter
import com.example.proyecto.entidad.Material
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolicitudActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var rvSolicitudes: RecyclerView

    private lateinit var apiServices: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_solicitud)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        button = findViewById(R.id.button)
        rvSolicitudes = findViewById(R.id.rvSolicitudes)

        apiServices = ApiUtils.getAPIService()

        listado()

        button.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
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
        apiServices.findAllMateriales().enqueue(object : Callback<List<Material>> {
            override fun onResponse(call: Call<List<Material>>, response: Response<List<Material>>) {
                val material = response.body()
                if (material != null) {
                    val adaptador = MaterialAdapter(material)
                    rvSolicitudes.layoutManager = LinearLayoutManager(this@SolicitudActivity)  // Usamos el contexto de la actividad
                    rvSolicitudes.adapter = adaptador
                } else {
                    showAlert("No se encontraron solicitudes.")
                }
            }

            override fun onFailure(call: Call<List<Material>>, t: Throwable) {
                showAlert("Error de conexión: ${t.localizedMessage}")
            }
        })
    }
}

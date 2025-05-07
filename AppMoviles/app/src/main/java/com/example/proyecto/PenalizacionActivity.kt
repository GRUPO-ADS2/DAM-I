package com.example.proyecto

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto.adaptador.PenalizacionAdapter
import com.example.proyecto.entidad.Penalizacion
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PenalizacionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PenalizacionAdapter
    private lateinit var btnVolver: Button
    private val apiService: ApiService = ApiUtils.getAPIService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_penalizacion)

        btnVolver = findViewById(R.id.buttonVol)
        recyclerView = findViewById(R.id.rvPenes)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadPenalizaciones()

        btnVolver.setOnClickListener {
            finish()
        }
    }

    private fun loadPenalizaciones() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    apiService.findAllPenalizaciones().execute()
                }
                if (response.isSuccessful) {
                    val penalizaciones = response.body() ?: emptyList()
                    adapter = PenalizacionAdapter(penalizaciones)
                    recyclerView.adapter = adapter
                } else {
                    // Manejar error
                }
            } catch (e: Exception) {
                // Manejar error
            }
        }
    }
}
package com.example.proyecto

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.entidad.Alumno
import com.example.proyecto.services.ApiService
import com.example.proyecto.utils.ApiUtils
import com.example.proyecto.utils.SessionManager
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var txtUsuario: TextInputEditText
    private lateinit var txtContrasena: TextInputEditText
    private lateinit var button: Button
    private lateinit var apiService: ApiService
    private var alumnos: List<Alumno> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        txtUsuario = findViewById(R.id.txtUsuario)
        txtContrasena = findViewById(R.id.txtContrasena)
        button = findViewById(R.id.button)
        apiService = ApiUtils.getAPIService()

        // Fetch users from the database
        fetchAlumnos()

        // Inside onCreate method of MainActivity
        val intent = Intent(this, FirebaseTokenService::class.java)
        startService(intent)

        button.setOnClickListener {
            val username = txtUsuario.text.toString()
            val password = txtContrasena.text.toString()
            if (validateUser(username, password)) {
                val alumno = alumnos.find { it.usuario.codUsuario.toString() == username }
                if (alumno != null) {
                    SessionManager.saveUser(this, alumno)
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Usuario no encontrado", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Nombre de Usuario o Contraseña incorrectas", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchAlumnos() {
        apiService.findAllAlumnos().enqueue(object : Callback<List<Alumno>> {
            override fun onResponse(call: Call<List<Alumno>>, response: Response<List<Alumno>>) {
                if (response.isSuccessful) {
                    alumnos = response.body() ?: listOf()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to fetch users", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Alumno>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun validateUser(username: String, password: String): Boolean {
        return alumnos.any { it.usuario.codUsuario.toString() == username && it.usuario.contrasenia == password }
    }
}
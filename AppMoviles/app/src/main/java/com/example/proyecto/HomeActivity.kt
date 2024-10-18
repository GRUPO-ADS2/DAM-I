package com.example.proyecto

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyecto.utils.SessionManager

class HomeActivity : AppCompatActivity(){

    private lateinit var btnSol: Button
    private lateinit var btnSeg: Button
    private lateinit var btnReg: Button
    private lateinit var btnUser: Button
    private lateinit var tvHolaUsu: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnSol=findViewById(R.id.btnSol)
        btnSeg=findViewById(R.id.btnSeg)
        btnReg=findViewById(R.id.btnReg)
        btnUser=findViewById(R.id.btnUser)
        tvHolaUsu = findViewById(R.id.tvHolaUsuario)

        // Get the user from SessionManager
        val alumno = SessionManager.getUser(this)
        if (alumno != null) {
            tvHolaUsu.text = "Hola, ${alumno.nombresApellidos}!"
        } else {
            tvHolaUsu.text = "Hola, Usuario!"
        }

        btnSol.setOnClickListener {
            var intent= Intent(this,SolicitudActivity::class.java)
            startActivity(intent)
        }
        btnSeg.setOnClickListener {
            var intent= Intent(this,SeguimientoActivity::class.java)
            startActivity(intent)
        }
        btnReg.setOnClickListener {
            var intent= Intent(this,RegistrarActivity::class.java)
            startActivity(intent)
        }
        btnUser.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("¿Seguro que quieres cerrar sesión?")
            builder.setMessage("Puedes regresar")

            builder.setPositiveButton(android.R.string.yes) { dialog, which ->
                SessionManager.clearUser(this) // Limpiar la sesión del usuario
                Toast.makeText(this, "Chau", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext, android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()
        }
    }
}
package com.example.proyecto

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HomeActivity : AppCompatActivity(){

    private lateinit var btnSol: Button
    private lateinit var btnSeg: Button
    private lateinit var btnReg: Button
    private lateinit var btnUser: Button


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
                    Toast.makeText(this,"Chau", Toast.LENGTH_LONG)
                    var intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
            }

            builder.setNegativeButton(android.R.string.no) { dialog, which ->
                Toast.makeText(applicationContext,
                    android.R.string.no, Toast.LENGTH_SHORT).show()
            }
            builder.show()

        }
    }
}
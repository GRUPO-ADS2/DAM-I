package com.example.proyecto

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
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
            var intent= Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }
}
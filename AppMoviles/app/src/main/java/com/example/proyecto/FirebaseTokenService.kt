package com.example.proyecto

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging

class FirebaseTokenService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Obtener el token de registro del dispositivo en segundo plano
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w("FirebaseTokenService", "Fetching FCM registration token failed", task.exception)
                    stopSelf()
                    return@addOnCompleteListener
                }

                // Obtener el nuevo token de registro de FCM
                val token = task.result
                Log.d("FirebaseTokenService", "FCM Registration Token: $token")
                // Guardar el token en tu servidor o base de datos
                stopSelf()
            }
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
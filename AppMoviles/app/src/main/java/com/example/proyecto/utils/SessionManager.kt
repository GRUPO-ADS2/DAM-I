package com.example.proyecto.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.proyecto.entidad.Alumno
import com.example.proyecto.entidad.Usuario

object SessionManager {
    private const val PREF_NAME = "user_session"
    private const val KEY_USER_ID = "user_id"
    private const val KEY_USER_NAME = "user_name"
    private const val KEY_USER_STATUS = "user_status"
    private const val KEY_USER_ROLE = "user_role"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUser(context: Context, alumno: Alumno) {
        val editor = getPreferences(context).edit()
        editor.putInt(KEY_USER_ID, alumno.usuarioCodUsuario)
        editor.putString(KEY_USER_NAME, alumno.nombresApellidos)
        editor.putString(KEY_USER_STATUS, alumno.estado)
        editor.putString(KEY_USER_ROLE, alumno.usuario.role)
        editor.apply()
    }

    fun getUser(context: Context): Alumno? {
        val prefs = getPreferences(context)
        val userId = prefs.getInt(KEY_USER_ID, -1)
        val userName = prefs.getString(KEY_USER_NAME, null)
        val userStatus = prefs.getString(KEY_USER_STATUS, null)
        val userRole = prefs.getString(KEY_USER_ROLE, null)

        return if (userId != -1 && userName != null && userStatus != null && userRole != null) {
            Alumno(userId, userName, userStatus, 0, Usuario(userId, userName, userRole))
        } else {
            null
        }
    }

    fun clearUser(context: Context) {
        val editor = getPreferences(context).edit()
        editor.clear()
        editor.apply()
    }
}
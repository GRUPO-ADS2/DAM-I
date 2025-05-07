package com.example.proyecto.services

import com.example.proyecto.dto.PenaDTO
import com.example.proyecto.dto.PresDTO
import com.example.proyecto.dto.SoliDTO
import com.example.proyecto.entidad.Alumno
import com.example.proyecto.entidad.Material
import com.example.proyecto.entidad.Penalizacion
import com.example.proyecto.entidad.Prestamo
import com.example.proyecto.entidad.Solicitud
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    //MATERIALES
    @GET("/materiales")
    fun findAllMateriales(): Call<List<Material>>


    //ALUMNOS
    @GET("/alumnos")
    fun findAllAlumnos(): Call<List<Alumno>>


    // SOLICITUDES
    @GET("/solicitudes")
    fun findAllSolicitudes(): Call<List<Solicitud>>
    @GET("/solicitud/{id}")
    fun findSolicitudById(@Path("id") idsolicitud: Int): Call<Solicitud>
    @POST("/solicitud")
    fun registrarSolicitud(@Body bean:SoliDTO):Call<Void>
    @PUT("/solicitud/{id}")
    fun actualizarEstadoSolicitud(@Path("id") idsolicitud:Int):Call<Void>
    @DELETE("/solicitud/{id}")
    fun deleteSolicitudById(@Path("id") idsolicitud:Int):Call<Void>


    // PRESTAMOS
    @GET("/prestamos")
    fun findAllPrestamos(): Call<List<Prestamo>>
    @GET("/prestamo/{id}")
    fun findPrestamoById(@Path("id") idPrestamo: Int): Call<Prestamo>
    @POST("/prestamo")
    fun registrarPrestamo(@Body bean:PresDTO):Call<Void>
    @PUT("/prestamo/{id}")
    fun actualizarPrestamo(@Path("id") idPrestamo:Int):Call<Void>
    @DELETE("/prestamo/{id}")
    fun deletePrestamoById(@Path("id") idPrestamo:Int):Call<Void>
    // DEVOLUCION
    @POST("/devolucion/{id}")
    fun registrarDevolucion(@Path("id") idPrestamo:Int):Call<Void>



    // PENALIZACIONES
    @GET("/penalizaciones")
    fun findAllPenalizaciones(): Call<List<Penalizacion>>
    @GET("/penalizacion/{id}")
    fun findPenalizacionById(@Path("id") idPenalizacion: Int): Call<Penalizacion>
    @POST("/penalizacion")
    fun registrarPenalizacion(@Body bean:PenaDTO):Call<Void>
    @PUT("/penalizacion/{id}")
    fun actualizarPenalizacion(@Path("id") idPenalizacion:Int):Call<Void>
    @DELETE("/penalizacion/{id}")
    fun deletePenalizacionById(@Path("id") idPenalizacion:Int):Call<Void>


}
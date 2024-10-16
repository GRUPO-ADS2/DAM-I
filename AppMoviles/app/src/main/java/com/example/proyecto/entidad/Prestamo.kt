package com.example.proyecto.entidad

class Prestamo(var idPrestamo: Int,
               var fechaPrestamo: String,  // Puede ser Date en lugar de String si usas una librería de fecha
               var estado: String,
               var fechaDevReal: String?,  // Nullable para manejar valores nulos
               var solicitud: Solicitud) {
}
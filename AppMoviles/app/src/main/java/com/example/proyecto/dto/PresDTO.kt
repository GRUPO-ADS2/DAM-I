package com.example.proyecto.dto

import java.time.LocalDateTime

data class PresDTO(
    val solicitudId: Int,
    val fechaPrestamo: LocalDateTime
)
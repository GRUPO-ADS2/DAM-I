package com.example.demo.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Prestamo")
public class Prestamo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPrestamo")
    private Integer idPrestamo;

    @Column(name = "FechaPrestamo", nullable = false)
    private LocalDateTime fechaPrestamo;

    @Column(name = "Estado", nullable = false, length = 15)
    private String estado;

    @Column(name = "Fecha_dev_real")
    private LocalDateTime fechaDevReal;

    @ManyToOne
    @JoinColumn(name = "Solicitud_idSolicitud", referencedColumnName = "IdSolicitud")
    private Solicitud solicitud;

    // Getters y Setters
}


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
@Table(name = "Penalizacion")
public class Penalizacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdPenalizacion")
    private Integer idPenalizacion;

    @Column(name = "FechaPenalizacion", nullable = false)
    private LocalDateTime fechaPenalizacion;

    @Column(name = "Descripcion", nullable = false, length = 255)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "Prestamo_idPrestamo", referencedColumnName = "IdPrestamo")
    private Prestamo prestamo;

    // Getters y Setters
}

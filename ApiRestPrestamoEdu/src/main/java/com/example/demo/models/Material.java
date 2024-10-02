package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Material")
public class Material {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodMaterial")
    private Integer codMaterial;

    @Column(name = "Nombre", nullable = false, length = 45)
    private String nombre;

    @Column(name = "Descripcion", nullable = false, length = 45)
    private String descripcion;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @Column(name = "Tipo", nullable = false, length = 25)
    private String tipo;

    // Getters y Setters
}


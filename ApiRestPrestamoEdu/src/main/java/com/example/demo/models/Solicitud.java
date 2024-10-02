package com.example.demo.models;

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
@Table(name = "Solicitud")
public class Solicitud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdSolicitud")
    private Integer idSolicitud;

    @Column(name = "Cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "Estado", nullable = false, length = 15)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "Material_CodMaterial", referencedColumnName = "CodMaterial")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "Alumno_Usuario_CodUsuario", referencedColumnName = "Usuario_CodUsuario")
    private Alumno alumno;

    // Getters y Setters
}


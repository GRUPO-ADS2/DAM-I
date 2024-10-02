package com.example.demo.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Alumno")
public class Alumno {

    @Id
    @Column(name = "Usuario_CodUsuario")
    private Integer usuarioCodUsuario;

    @Column(name = "NombresApellidos", nullable = false, length = 90)
    private String nombresApellidos;

    @Column(name = "Estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "DiasInhabilitado")
    private Integer diasInhabilitado = 0;

    @OneToOne
    @JoinColumn(name = "Usuario_CodUsuario", referencedColumnName = "CodUsuario")
    private Usuario usuario;

    // Getters y Setters
}


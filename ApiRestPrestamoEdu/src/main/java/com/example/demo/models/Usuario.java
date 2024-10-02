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
@Table(name = "Usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CodUsuario")
    private Integer codUsuario;

    @Column(name = "Contrasenia", nullable = false, length = 20)
    private String contrasenia;

    @Column(name = "Role", nullable = false, length = 20)
    private String role = "Alumno";
    

}

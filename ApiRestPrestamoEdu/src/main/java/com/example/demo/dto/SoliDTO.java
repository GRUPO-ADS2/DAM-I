package com.example.demo.dto;


import com.example.demo.models.Alumno;
import com.example.demo.models.Material;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class SoliDTO {
    private Integer idSolicitud;
    private Integer cantidad;
    private String estado;
    private Material material;
    private Alumno alumno;
}

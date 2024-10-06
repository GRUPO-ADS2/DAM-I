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
    private int alumnoCodUsuario;
    private int materialCod;
    private int cantidad;
}

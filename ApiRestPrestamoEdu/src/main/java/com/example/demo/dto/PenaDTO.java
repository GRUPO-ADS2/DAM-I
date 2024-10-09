package com.example.demo.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PenaDTO {
    private int prestamoId;
    private LocalDateTime fechaPenalizacion;
    private String descripcion;
}

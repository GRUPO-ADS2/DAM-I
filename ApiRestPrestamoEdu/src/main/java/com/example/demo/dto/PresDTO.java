package com.example.demo.dto;
import com.example.demo.models.Solicitud;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class PresDTO {
    private Integer idPrestamo;
    private LocalDateTime fechaPrestamo;
    private String estado;
    private LocalDateTime fechaDevReal;
    private Solicitud solicitud;
}

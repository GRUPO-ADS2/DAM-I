package com.example.demo.service;

import com.example.demo.dto.PresDTO;
import com.example.demo.models.Prestamo;

import java.time.LocalDateTime;
import java.util.List;

public interface IPrestamoServices {
    List<Prestamo> GetAllPrestamos();
    void registrarPrestamo(PresDTO prestamoDTO);
    void actualizarPrestamo(int prestamoId, LocalDateTime nuevaFechaPrestamo);
    Prestamo FindPrestamoById(int id);
    void registrarDevolucion(int prestamoId, LocalDateTime fechaDevolucion);
    Integer deletePrestamo(Integer id);
}

package com.example.demo.service;

import com.example.demo.models.Prestamo;

import java.util.List;

public interface IPrestamoServices {
    List<Prestamo> GetAllPrestamos();
    Prestamo SavePrestamo(Prestamo entity);
    Prestamo FindPrestamoById(int id);
    Integer updatePrestamo(Integer id, Prestamo prestamo);
    Integer deletePrestamo(Integer id);
}

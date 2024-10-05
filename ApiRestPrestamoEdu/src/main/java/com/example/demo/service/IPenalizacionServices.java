package com.example.demo.service;

import com.example.demo.models.Penalizacion;
import com.example.demo.models.Prestamo;

import java.util.List;

public interface IPenalizacionServices {
    List<Penalizacion> GetAllPenalizaciones();
    Penalizacion SavePenalizacion(Penalizacion entity);
    Penalizacion FindPenalizacionById(int id);
    Integer updatePenalizacion(Integer id, Penalizacion penalizacion);
    Integer deletePenalizacion(Integer id);
}

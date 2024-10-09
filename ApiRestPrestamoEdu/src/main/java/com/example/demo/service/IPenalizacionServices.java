package com.example.demo.service;

import com.example.demo.dto.PenaDTO;
import com.example.demo.models.Penalizacion;

import java.time.LocalDateTime;
import java.util.List;

public interface IPenalizacionServices {
    List<Penalizacion> GetAllPenalizaciones();
    void registrarPenalizacion(PenaDTO penaDTO);
    Penalizacion FindPenalizacionById(int id);
    Integer updatePenalizacion(Integer id, Penalizacion penalizacion);
    Integer deletePenalizacion(Integer id);
}

package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Penalizacion;

import java.time.LocalDateTime;

@Repository
public interface IPenalizacionRepository extends JpaRepository<Penalizacion, Integer> {
    @Procedure(name = "registrarPenalizacion")
    void registrarPenalizacion(int prestamoId, LocalDateTime fechaPenalizacion, String descripcion);
}

package com.example.demo.service.impl;

import com.example.demo.dto.PresDTO;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;
import com.example.demo.repository.IPrestamoRepository;
import com.example.demo.repository.ISolicitudRepository;
import com.example.demo.service.IPrestamoServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServices implements IPrestamoServices {
    IPrestamoRepository _prestamoRepository;

    @Autowired
    public PrestamoServices(IPrestamoRepository prestamoRepository){_prestamoRepository = prestamoRepository;}


    @Override
    public List<Prestamo> GetAllPrestamos() {
        return _prestamoRepository.findAll();
    }

    @Override
    @Transactional
    public void registrarPrestamo(PresDTO presDTO) {
        try {
            LocalDateTime fechaPrestamoActual = LocalDateTime.now();

            _prestamoRepository.registrarPrestamo(
                    presDTO.getSolicitudId(),
                    fechaPrestamoActual
            );
        } catch (Exception e) {
            System.err.println("Error al registrar el prestamo: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void actualizarPrestamo(int prestamoId, LocalDateTime _nuevaFechaPrestamo) {
        LocalDateTime nuevaFechaPrestamo = LocalDateTime.now();
        _prestamoRepository.actualizarPrestamo(prestamoId, nuevaFechaPrestamo);
    }


    @Override
    public Prestamo FindPrestamoById(int id) {
        Optional<Prestamo> rowInDB = _prestamoRepository.findById(id);
        if (rowInDB.isPresent())
            return rowInDB.get();
        else
            return new Prestamo();
    }

    @Override
    @Transactional
    public void registrarDevolucion(int prestamoId, LocalDateTime fechaDevolucion) {
        try {
            LocalDateTime _fechaDevolucion = LocalDateTime.now();

            _prestamoRepository.registrarDevolucion(
                    prestamoId,_fechaDevolucion
            );
        } catch (Exception e) {
            System.err.println("Error al registrar la devolución del prestamo: "
                    + e.getMessage());
        }
    }


    @Override
    public Integer deletePrestamo(Integer id) {
        Optional<Prestamo> optionalPrestamo = _prestamoRepository.findById(id);
        if (optionalPrestamo.isPresent()) {
            _prestamoRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }
}

package com.example.demo.service.impl;

import com.example.demo.dto.PenaDTO;
import com.example.demo.models.Penalizacion;
import com.example.demo.models.Prestamo;
import com.example.demo.repository.IPenalizacionRepository;
import com.example.demo.repository.IPrestamoRepository;
import com.example.demo.service.IPenalizacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PenalizacionServices implements IPenalizacionServices {

    IPenalizacionRepository _penalizacionRepository;

    @Autowired
    public PenalizacionServices(IPenalizacionRepository penalizacionRepository){
        _penalizacionRepository = penalizacionRepository;
    }

    @Override
    public List<Penalizacion> GetAllPenalizaciones() {
        return _penalizacionRepository.findAll();
    }

    @Override
    @Transactional
    public void registrarPenalizacion(PenaDTO penaDTO) {
        try {
            LocalDateTime _fechaPenalizacion = LocalDateTime.now();

            _penalizacionRepository.registrarPenalizacion(
                    penaDTO.getPrestamoId(),
                    _fechaPenalizacion,
                    penaDTO.getDescripcion()
            );
        } catch (Exception e) {
            System.err.println("Error al registrar la penalizacion: " + e.getMessage());
        }
    }

    @Override
    public Penalizacion FindPenalizacionById(int id) {
        Optional<Penalizacion> rowInDB = _penalizacionRepository.findById(id);
        if (rowInDB.isPresent())
            return rowInDB.get();
        else
            return new Penalizacion();
    }

    @Override
    public Integer updatePenalizacion(Integer id, Penalizacion penalizacion) {
        Optional<Penalizacion> existingPenalizacion = _penalizacionRepository.findById(id);
        if (existingPenalizacion.isPresent()) {
            Penalizacion PenalizacionToUpdate = existingPenalizacion.get();
            PenalizacionToUpdate.setFechaPenalizacion(penalizacion.getFechaPenalizacion());
            PenalizacionToUpdate.setDescripcion(penalizacion.getDescripcion());
            _penalizacionRepository.save(PenalizacionToUpdate);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Integer deletePenalizacion(Integer id) {
        Optional<Penalizacion> optionalPenalizacion = _penalizacionRepository.findById(id);
        if (optionalPenalizacion.isPresent()) {
            _penalizacionRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }
}

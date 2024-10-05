package com.example.demo.service.impl;

import com.example.demo.models.Penalizacion;
import com.example.demo.models.Prestamo;
import com.example.demo.repository.IPenalizacionRepository;
import com.example.demo.repository.IPrestamoRepository;
import com.example.demo.service.IPenalizacionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Penalizacion SavePenalizacion(Penalizacion entity) {
        Penalizacion penalizacionSaved = _penalizacionRepository.save(entity);
        return penalizacionSaved;
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

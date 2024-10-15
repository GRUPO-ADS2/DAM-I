package com.example.demo.service.impl;

import com.example.demo.dto.SoliDTO;
import com.example.demo.models.Alumno;
import com.example.demo.models.Material;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;
import com.example.demo.repository.IAlumnoRepository;
import com.example.demo.repository.IMaterialRepository;
import com.example.demo.repository.IPrestamoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ISolicitudRepository;
import com.example.demo.service.ISolicitudServices;

import jakarta.annotation.PostConstruct;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudServices implements ISolicitudServices {

    ISolicitudRepository _solicitudRepository;
    IAlumnoRepository _alumnoRepository;
    IMaterialRepository _materialRepository;

    @Autowired
    public SolicitudServices(ISolicitudRepository solicitudRepository,IAlumnoRepository alumnoRepository,IMaterialRepository materialRepository){
        _solicitudRepository = solicitudRepository;
        _alumnoRepository = alumnoRepository;
        _materialRepository = materialRepository;
    }
    @Override
    public List<Solicitud> GetAllSolicitudes() {
        return _solicitudRepository.findAll();
    }

    @Override
    public List<Material> GetAllMateriales() {
        return _materialRepository.findAll();
    }

    @Override
    public List<Alumno> GetAllAlumnos() {
        return _alumnoRepository.findAll();
    }

    @Override
    public Solicitud SaveSolicitud(Solicitud entity) {
        Solicitud solicitudSaved = _solicitudRepository.save(entity);
        return solicitudSaved;
    }

    @Override
    public Solicitud FindSolicitudById(int id) {
        Optional<Solicitud> rowInDB = _solicitudRepository.findById(id);
        if (rowInDB.isPresent())
            return rowInDB.get();
        else
            return new Solicitud();
    }

    @Override
    @Transactional
    public void registrarSolicitud(SoliDTO soliDTO) {
        try {
            _solicitudRepository.registrarSolicitud(
                    soliDTO.getAlumnoCodUsuario(),
                    soliDTO.getMaterialCod(),
                    soliDTO.getCantidad()
            );
        } catch (Exception e) {
            System.err.println("Error al registrar la solicitud: " + e.getMessage());
        }
    }
    @Override
    public void actualizarEstadoSolicitud(Integer solicitudId, String nuevoEstado) {
        _solicitudRepository.actualizarEstadoSolicitud(solicitudId, nuevoEstado);
    }

    @Override
    public Integer updateSolicitud(Integer id, Solicitud solicitud) {
        Optional<Solicitud> existingSolicitud = _solicitudRepository.findById(id);
        if (existingSolicitud.isPresent()) {
            Solicitud SolicitudToUpdate = existingSolicitud.get();
            SolicitudToUpdate.setCantidad(solicitud.getCantidad());
            SolicitudToUpdate.setMaterial(solicitud.getMaterial());
            _solicitudRepository.save(SolicitudToUpdate);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Integer deleteSolicitud(Integer id) {
        Optional<Solicitud> optionalSolicitud = _solicitudRepository.findById(id);
        if (optionalSolicitud.isPresent()) {
            _solicitudRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }

}

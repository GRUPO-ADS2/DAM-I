package com.example.demo.service.impl;

import com.example.demo.dto.SoliAndPresDTO;
import com.example.demo.dto.SoliDTO;
import com.example.demo.models.Material;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;
import com.example.demo.repository.IMaterialRepository;
import com.example.demo.repository.IPrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ISolicitudRepository;
import com.example.demo.service.ISolicitudServices;

import jakarta.annotation.PostConstruct;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SolicitudServices implements ISolicitudServices {

    ISolicitudRepository _solicitudRepository;
    IMaterialRepository _materialRepository;

    @Autowired
    public SolicitudServices(ISolicitudRepository solicitudRepository,IMaterialRepository materialRepository){
        _solicitudRepository = solicitudRepository;
        _materialRepository = materialRepository;
    }
    @Override
    public List<Solicitud> GetAllSolicitudes() {
        return _solicitudRepository.findAll();
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
    public void registrarSolicitud(SoliDTO solicitudDTO) {
        _solicitudRepository.registrarSolicitud(
                solicitudDTO.getAlumnoCodUsuario(),
                solicitudDTO.getMaterialCod(),
                solicitudDTO.getCantidad());
        /* Buscar el material en la base de datos
        Optional<Material> materialOpt = _materialRepository.findById(solicitudDTO.getMaterial().getCodMaterial());

        if (!materialOpt.isPresent()) {
            throw new IllegalArgumentException("Material no encontrado");
        }

        Material material = materialOpt.get();

        // Verificar si hay suficiente stock
        if (material.getStock() < solicitudDTO.getCantidad()) {
            throw new IllegalArgumentException("Stock insuficiente para el material solicitado");
        }

        // Restar del stock del material
        material.setStock(material.getStock() - solicitudDTO.getCantidad());
        _materialRepository.save(material);

        // Crear una nueva solicitud
        Solicitud nuevaSolicitud = new Solicitud();
        nuevaSolicitud.setAlumno(solicitudDTO.getAlumno());
        nuevaSolicitud.setMaterial(material);
        nuevaSolicitud.setCantidad(solicitudDTO.getCantidad());
        nuevaSolicitud.setEstado("Pendiente");

        // Guardar la solicitud en la base de datos
        return _solicitudRepository.save(nuevaSolicitud);*/
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


    @PostConstruct
    public void testProcedure() {
        // Prueba el procedimiento almacenado con valores de ejemplo
        try {
            Integer p_alumno_codUsuario = 1; // Código de usuario del alumno
            Integer p_material_cod = 100001; // Código de material
            int p_cantidad = 1; // Cantidad de material solicitado

            // Llamada al procedimiento almacenado
            _solicitudRepository.registrarSolicitud(p_alumno_codUsuario, p_material_cod, p_cantidad);

            System.out.println("Procedure ejecutado correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al ejecutar el procedimiento.");
        }
    }
	@Override
	public void solicitarPrestamo(Integer p_alumno_codUsuario, Integer p_material_cod, int p_cantidad) {
		// TODO Auto-generated method stub
	}



}

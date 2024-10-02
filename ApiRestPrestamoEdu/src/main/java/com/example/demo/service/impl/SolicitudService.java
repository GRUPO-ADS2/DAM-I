package com.example.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ISolicitudRepository;
import com.example.demo.service.ISolicitudService;

import jakarta.annotation.PostConstruct;

@Service
public class SolicitudService implements ISolicitudService {
	
	@Autowired
    private ISolicitudRepository solicitudRepository;

    @PostConstruct
    public void testProcedure() {
        // Prueba el procedimiento almacenado con valores de ejemplo
        try {
            Integer p_alumno_codUsuario = 1; // Código de usuario del alumno
            Integer p_material_cod = 100001; // Código de material
            int p_cantidad = 1; // Cantidad de material solicitado
            
            // Llamada al procedimiento almacenado
            solicitudRepository.registrarSolicitud(p_alumno_codUsuario, p_material_cod, p_cantidad);
            
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

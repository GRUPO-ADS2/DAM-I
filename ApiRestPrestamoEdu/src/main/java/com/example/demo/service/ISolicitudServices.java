package com.example.demo.service;

import com.example.demo.dto.SoliAndPresDTO;
import com.example.demo.dto.SoliDTO;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;

import java.util.List;

public interface ISolicitudServices {
	void solicitarPrestamo(Integer p_alumno_codUsuario, Integer p_material_cod, int p_cantidad);
	List<Solicitud> GetAllSolicitudes();
	Solicitud SaveSolicitud(Solicitud entity);
	Solicitud FindSolicitudById(int id);
	void registrarSolicitud(SoliDTO solicitudDTO);
	void actualizarEstadoSolicitud(Integer solicitudId, String nuevoEstado);
	Integer updateSolicitud(Integer id, Solicitud solicitud);
	Integer deleteSolicitud(Integer id);
}

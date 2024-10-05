package com.example.demo.service;

import com.example.demo.dto.SoliAndPresDTO;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;

import java.util.List;

public interface ISolicitudServices {
	void solicitarPrestamo(Integer p_alumno_codUsuario, Integer p_material_cod, int p_cantidad);
	List<Solicitud> GetAllSolicitudes();
	Solicitud SaveSolicitud(Solicitud entity);
	Solicitud FindSolicitudById(int id);
	Integer updateSolicitud(Integer id, Solicitud solicitud);
	Integer deleteSolicitud(Integer id);
	Solicitud SaveSolicitudAndPrestamo(SoliAndPresDTO soliAndPresDTO);
}

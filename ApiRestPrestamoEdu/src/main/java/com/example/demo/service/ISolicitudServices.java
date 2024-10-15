package com.example.demo.service;

import com.example.demo.dto.SoliDTO;
import com.example.demo.models.Alumno;
import com.example.demo.models.Material;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;

import java.util.List;

public interface ISolicitudServices {
	List<Solicitud> GetAllSolicitudes();
	List<Material> GetAllMateriales();
	List<Alumno> GetAllAlumnos();
	Solicitud SaveSolicitud(Solicitud entity);
	Solicitud FindSolicitudById(int id);
	void registrarSolicitud(SoliDTO solicitudDTO);
	void actualizarEstadoSolicitud(Integer solicitudId, String nuevoEstado);
	Integer updateSolicitud(Integer id, Solicitud solicitud);
	Integer deleteSolicitud(Integer id);
}

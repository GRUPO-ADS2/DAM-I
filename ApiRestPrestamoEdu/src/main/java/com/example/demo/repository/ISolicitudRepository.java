package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.example.demo.models.Solicitud;

public interface ISolicitudRepository extends JpaRepository<Solicitud, Integer> {
	
	@Procedure
	void registrarSolicitud(Integer p_alumno_codUsuario, Integer p_material_cod, int p_cantidad);
	
	@Procedure
	void actualizarEstadoSolicitud(Integer p_solicitud_id, String p_nuevo_estado);
}

package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

import com.example.demo.models.Solicitud;
import org.springframework.stereotype.Repository;

@Repository
public interface ISolicitudRepository extends JpaRepository<Solicitud, Integer> {

	@Procedure(name = "registrarSolicitud") // Asegúrate de que el nombre sea correcto
	void registrarSolicitud(Integer p_alumno_codUsuario, Integer p_material_cod, int p_cantidad);

	@Procedure(name = "actualizarEstadoSolicitud") // Asegúrate de que el nombre sea correcto
	void actualizarEstadoSolicitud(Integer p_solicitud_id, String p_nuevo_estado);
}

package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Prestamo;
@Repository
public interface IPrestamoRepository extends JpaRepository<Prestamo, Integer> {
	@Procedure
	void registrarPrestamo(Integer p_solicitud_id, LocalDateTime p_fecha_prestamo);
	@Procedure
	void registrarDevolucion(Integer p_prestamo_id, LocalDateTime p_fecha_devolucion);
	@Procedure
	void registrarPenalizacion(Integer p_prestamo_id, LocalDateTime p_fecha_penalizacion, String p_descripcion);
	@Procedure
	void actualizarPrestamo(Integer p_prestamo_id, LocalDateTime p_nueva_fecha_prestamo);
}

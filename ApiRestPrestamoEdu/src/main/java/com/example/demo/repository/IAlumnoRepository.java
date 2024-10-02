package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.models.Alumno;

@Repository
public interface IAlumnoRepository extends JpaRepository<Alumno, Integer> {
	
}

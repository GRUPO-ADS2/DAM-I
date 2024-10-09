package com.example.demo.rest;

import com.example.demo.dto.PresDTO;
import com.example.demo.dto.SoliDTO;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;
import com.example.demo.service.IPrestamoServices;
import com.example.demo.service.ISolicitudServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PrestamoController {
    IPrestamoServices prestamoServices;
    @Autowired
    public PrestamoController(IPrestamoServices prestamoServices){this.prestamoServices=prestamoServices;}

    @GetMapping("/prestamos")
    public List<Prestamo> getAll() {return prestamoServices.GetAllPrestamos();}

    @GetMapping("/prestamo/{id}")
    public Prestamo getAll(@PathVariable int id) {
        return prestamoServices.FindPrestamoById(id);
    }

    @PostMapping("/prestamo")
    public ResponseEntity<String> registrarPrestamo(@RequestBody PresDTO presDTO) {
        try {
            prestamoServices.registrarPrestamo(presDTO);
            return ResponseEntity.ok("Prestamo registrado exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al registrar el préstamo: " + e.getMessage());
        }
    }
    @PostMapping("/devolucion/{id}")
    public ResponseEntity<String> registrarDevolucion(@PathVariable Integer id) {
        try {
            prestamoServices.registrarDevolucion(id, LocalDateTime.now());
            return ResponseEntity.ok("Prestamo devuelto exitosamente");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al devolver el préstamo: " + e.getMessage());
        }
    }
    @PutMapping("/prestamo/{id}")
    public ResponseEntity<?> actualizarPrestamo(@PathVariable Integer id) {
        try {
            // Llamar al servicio para actualizar el préstamo usando la fecha actual del sistema
            prestamoServices.actualizarPrestamo(id, LocalDateTime.now());
            return ResponseEntity.ok().body("Prestamo actualizado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el estado: " + e.getMessage());
        }
    }


    @DeleteMapping("/prestamo/{id}")
    public ResponseEntity<Integer> deletePrestamo(@PathVariable Integer id) {
        Integer deleted = prestamoServices.deletePrestamo(id);
        if (deleted == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.example.demo.rest;

import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;
import com.example.demo.service.IPrestamoServices;
import com.example.demo.service.ISolicitudServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Prestamo saveSolicitud(@RequestBody Prestamo entity) {
        return prestamoServices.SavePrestamo(entity);
    }

    @PutMapping("/prestamo/{id}")
    public ResponseEntity<Integer> updatePrestamo(@PathVariable Integer id, @RequestBody Prestamo prestamo) {
        Integer updated = prestamoServices.updatePrestamo(id, prestamo);
        if (updated == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

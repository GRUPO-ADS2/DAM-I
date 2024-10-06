package com.example.demo.rest;

import com.example.demo.dto.SoliAndPresDTO;
import com.example.demo.dto.SoliDTO;
import com.example.demo.models.Prestamo;
import com.example.demo.models.Solicitud;
import com.example.demo.service.ISolicitudServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class SolicitudController {

    ISolicitudServices solicitudServices;

    @Autowired
    public SolicitudController(ISolicitudServices solicitudServices){this.solicitudServices=solicitudServices;}

    @GetMapping("/solicitudes")
    public List<Solicitud> getAll() {return solicitudServices.GetAllSolicitudes();}

    @GetMapping("/solicitud/{id}")
    public Solicitud getAll(@PathVariable int id) {
        return solicitudServices.FindSolicitudById(id);
    }

    @PostMapping("/solicitud")
    public ResponseEntity<Solicitud> registrarSolicitud(@RequestBody SoliDTO solicitudDTO) {
        if (solicitudDTO == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        try {
            solicitudServices.registrarSolicitud(solicitudDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    /*
    @PostMapping("/solicitud")
    public Solicitud saveSolicitud(@RequestBody Solicitud entity) {
        return solicitudServices.SaveSolicitud(entity);
    }

    @PutMapping("/solicitud/{id}")
    public ResponseEntity<Integer> updateSolicitud(@PathVariable Integer id, @RequestBody Solicitud solicitud) {
        Integer updated = solicitudServices.updateSolicitud(id, solicitud);
        if (updated == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }*/
@PutMapping("/solicitud/{id}")
    public ResponseEntity<?> actualizarEstadoSolicitud(@PathVariable Integer id, @RequestParam String nuevoEstado) {
        try {
            solicitudServices.actualizarEstadoSolicitud(id, nuevoEstado);
            return ResponseEntity.ok().body("{success: true}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al actualizar el estado: " + e.getMessage());
        }
    }

    @DeleteMapping("/solicitud/{id}")
    public ResponseEntity<Integer> deleteSolicitud(@PathVariable Integer id) {
        Integer deleted = solicitudServices.deleteSolicitud(id);
        if (deleted == 1) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}

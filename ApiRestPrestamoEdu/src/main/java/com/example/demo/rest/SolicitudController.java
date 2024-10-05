package com.example.demo.rest;

import com.example.demo.dto.SoliAndPresDTO;
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

    @PostMapping("/solicitud/prestamo")
    public Solicitud saveSolicitudAndPrestamo(@RequestBody SoliAndPresDTO soliAndPresDTO) {
        return solicitudServices.SaveSolicitudAndPrestamo(soliAndPresDTO);
    }
}

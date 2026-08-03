package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.request.ServicioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.ServicioResponseDTO;
import com.trainer.trainer_booking_api.service.ServicioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService servicioService;

    public ServicioController(ServicioService servicioService) {
        this.servicioService = servicioService;
    }

    @GetMapping
    public List<ServicioResponseDTO> obtenerTodos() {
        return servicioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ServicioResponseDTO obtenerPorId(@PathVariable Integer id) {
        return servicioService.obtenerPorId(id);
    }

    @GetMapping("/entrenador/{idEntrenador}")
    public List<ServicioResponseDTO> obtenerPorEntrenador(@PathVariable Integer idEntrenador) {
        return servicioService.obtenerPorEntrenador(idEntrenador);
    }

    @PostMapping
    public ResponseEntity<ServicioResponseDTO> crearServicio(@Valid @RequestBody ServicioRequestDTO dto) {
        ServicioResponseDTO creado = servicioService.crearServicio(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioResponseDTO> actualizarServicio(
            @PathVariable Integer id,
            @Valid @RequestBody ServicioRequestDTO dto) {
        ServicioResponseDTO actualizado = servicioService.actualizarServicio(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer id) {
        servicioService.eliminarServicio(id);
        return ResponseEntity.noContent().build();
    }
}
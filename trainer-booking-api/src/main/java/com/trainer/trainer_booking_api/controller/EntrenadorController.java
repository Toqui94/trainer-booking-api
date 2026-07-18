package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.request.EntrenadorRequestDTO;
import com.trainer.trainer_booking_api.dto.response.EntrenadorResponseDTO;
import com.trainer.trainer_booking_api.service.EntrenadorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

    // ========== READ  ==========
    @GetMapping
    public List<EntrenadorResponseDTO> obtenerTodos() {
        return entrenadorService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public EntrenadorResponseDTO obtenerPorId(@PathVariable Integer id) {
        return entrenadorService.obtenerPorId(id);
    }

    @GetMapping("/ciudad/{ciudad}")
    public List<EntrenadorResponseDTO> obtenerPorCiudad(@PathVariable String ciudad) {
        return entrenadorService.obtenerPorCiudad(ciudad);
    }

    @GetMapping("/verificacion/{estado}")
    public List<EntrenadorResponseDTO> obtenerPorEstadoVerificacion(@PathVariable String estado) {
        return entrenadorService.obtenerPorEstadoVerificacion(estado);
    }

    // ========== CREATE ==========
    @PostMapping
    public ResponseEntity<EntrenadorResponseDTO> crearEntrenador(@Valid @RequestBody EntrenadorRequestDTO dto) {
        EntrenadorResponseDTO creado = entrenadorService.crearEntrenador(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ========== UPDATE ==========
    @PutMapping("/{id}")
    public ResponseEntity<EntrenadorResponseDTO> actualizarEntrenador(
            @PathVariable Integer id,
            @Valid @RequestBody EntrenadorRequestDTO dto) {
        
        EntrenadorResponseDTO actualizado = entrenadorService.actualizarEntrenador(id, dto);
        return ResponseEntity.ok(actualizado);
    }

    // ========== DELETE ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEntrenador(@PathVariable Integer id) {
        entrenadorService.eliminarEntrenador(id);
        return ResponseEntity.noContent().build();
    }
}
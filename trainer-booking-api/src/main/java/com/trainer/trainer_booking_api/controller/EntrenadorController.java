package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.response.EntrenadorResponseDTO;
import com.trainer.trainer_booking_api.service.EntrenadorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    private final EntrenadorService entrenadorService;

    public EntrenadorController(EntrenadorService entrenadorService) {
        this.entrenadorService = entrenadorService;
    }

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
}
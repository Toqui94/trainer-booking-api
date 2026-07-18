package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.entity.Entrenador;
import com.trainer.trainer_booking_api.repository.EntrenadorRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entrenadores")
public class EntrenadorController {

    private final EntrenadorRepository entrenadorRepository;

    public EntrenadorController(EntrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    // GET /api/entrenadores -> Devuelve TODOS los entrenadores
    @GetMapping
    public List<Entrenador> obtenerTodos() {
        return entrenadorRepository.findAll();
    }

    // GET /api/entrenadores/1 -> Devuelve el entrenador con ID 1
    @GetMapping("/{id}")
    public Entrenador obtenerPorId(@PathVariable Integer id) {
        return entrenadorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrenador no encontrado con id: " + id));
    }

    // GET /api/entrenadores/ciudad/Bogota -> Filtra entrenadores por ciudad
    @GetMapping("/ciudad/{ciudad}")
    public List<Entrenador> obtenerPorCiudad(@PathVariable String ciudad) {
        return entrenadorRepository.findByCiudad(ciudad);
    }

    // GET /api/entrenadores/verificacion/APROBADO -> Filtra por estado de verificación
    @GetMapping("/verificacion/{estado}")
    public List<Entrenador> obtenerPorEstadoVerificacion(@PathVariable String estado) {
        return entrenadorRepository.findByEstadoVerificacion(estado);
    }
}
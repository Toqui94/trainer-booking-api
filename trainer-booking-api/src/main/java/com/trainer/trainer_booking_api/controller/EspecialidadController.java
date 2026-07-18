package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.entity.Especialidad;
import com.trainer.trainer_booking_api.repository.EspecialidadRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadRepository especialidadRepository;

    // Spring inyecta automáticamente el Repository en el constructor
    public EspecialidadController(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    // GET /api/especialidades -> Devuelve TODAS las especialidades
    @GetMapping
    public List<Especialidad> obtenerTodas() {
        return especialidadRepository.findAll();
    }

    // GET /api/especialidades/1 -> Devuelve la especialidad con ID 1
    @GetMapping("/{id}")
    public Especialidad obtenerPorId(@PathVariable Integer id) {
        return especialidadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Especialidad no encontrada con id: " + id));
    }
}
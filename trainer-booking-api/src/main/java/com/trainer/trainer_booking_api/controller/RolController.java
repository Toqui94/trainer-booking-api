package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.entity.Rol;
import com.trainer.trainer_booking_api.repository.RolRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolRepository rolRepository;

    // Inyección de dependencias por constructor
    // Spring "inyecta" automáticamente el Repository aquí
    public RolController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // GET /api/roles -> Devuelve TODOS los roles
    @GetMapping
    public List<Rol> obtenerTodos() {
        return rolRepository.findAll();
    }

    // GET /api/roles/1 -> Devuelve el rol con ID 1
    @GetMapping("/{id}")
    public Rol obtenerPorId(@PathVariable Integer id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado con id: " + id));
    }
}

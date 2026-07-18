package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.response.RolResponseDTO;
import com.trainer.trainer_booking_api.service.RolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<RolResponseDTO> obtenerTodos() {
        return rolService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public RolResponseDTO obtenerPorId(@PathVariable Integer id) {
        return rolService.obtenerPorId(id);
    }
}
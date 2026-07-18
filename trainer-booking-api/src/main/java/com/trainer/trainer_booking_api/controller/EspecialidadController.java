package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.response.EspecialidadResponseDTO;
import com.trainer.trainer_booking_api.service.EspecialidadService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService especialidadService;

    public EspecialidadController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @GetMapping
    public List<EspecialidadResponseDTO> obtenerTodas() {
        return especialidadService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public EspecialidadResponseDTO obtenerPorId(@PathVariable Integer id) {
        return especialidadService.obtenerPorId(id);
    }
}
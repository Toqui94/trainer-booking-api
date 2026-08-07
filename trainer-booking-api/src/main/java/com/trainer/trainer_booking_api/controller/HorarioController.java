package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.request.HorarioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.HorarioResponseDTO;
import com.trainer.trainer_booking_api.service.HorarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/horarios")
public class HorarioController {

    private final HorarioService horarioService;

    public HorarioController(HorarioService horarioService) {
        this.horarioService = horarioService;
    }

    @GetMapping("/entrenador/{idEntrenador}")
    public List<HorarioResponseDTO> obtenerPorEntrenador(@PathVariable Integer idEntrenador) {
        return horarioService.obtenerPorEntrenador(idEntrenador);
    }

    @PostMapping
    public ResponseEntity<HorarioResponseDTO> crearHorario(@Valid @RequestBody HorarioRequestDTO dto) {
        HorarioResponseDTO creado = horarioService.crearHorario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Integer id) {
        horarioService.eliminarHorario(id);
        return ResponseEntity.noContent().build();
    }
}

package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.request.ReservaRequestDTO;
import com.trainer.trainer_booking_api.dto.response.ReservaResponseDTO;
import com.trainer.trainer_booking_api.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public List<ReservaResponseDTO> obtenerTodas() {
        return reservaService.obtenerTodas();
    }

    @GetMapping("/{id}")
    public ReservaResponseDTO obtenerPorId(@PathVariable Integer id) {
        return reservaService.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> crear(@Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservaService.crearReserva(dto));
    }

    @PostMapping("/{id}/confirmar")
    public ResponseEntity<ReservaResponseDTO> confirmar(@PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.confirmarReserva(id));
    }

    @PostMapping("/{id}/cancelar")
    public ResponseEntity<ReservaResponseDTO> cancelar(@PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.cancelarReserva(id));
    }

    @PostMapping("/{id}/realizar")
    public ResponseEntity<ReservaResponseDTO> marcarRealizada(@PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.marcarRealizada(id));
    }
}

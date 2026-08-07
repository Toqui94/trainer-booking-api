package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.request.PagoRequestDTO;
import com.trainer.trainer_booking_api.dto.response.PagoResponseDTO;
import com.trainer.trainer_booking_api.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @GetMapping("/reserva/{idReserva}")
    public PagoResponseDTO obtenerPorReserva(@PathVariable Integer idReserva) {
        return pagoService.obtenerPorReserva(idReserva);
    }

    @PostMapping("/{id}/confirmar")
    public PagoResponseDTO confirmarPago(@PathVariable Integer id, @Valid @RequestBody PagoRequestDTO dto) {
        return pagoService.confirmarPago(id, dto);
    }
}
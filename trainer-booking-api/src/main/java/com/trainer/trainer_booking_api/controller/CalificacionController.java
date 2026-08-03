package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.entity.Calificacion;
import com.trainer.trainer_booking_api.service.CalificacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionController {

    private final CalificacionService calificacionService;

    public CalificacionController(CalificacionService calificacionService) {
        this.calificacionService = calificacionService;
    }

    @PostMapping("/reserva/{idReserva}")
    public ResponseEntity<Calificacion> calificar(
            @PathVariable Integer idReserva,
            @RequestParam Integer puntuacion,
            @RequestParam(required = false) String comentario) {
        return ResponseEntity.ok(calificacionService.calificar(idReserva, puntuacion, comentario));
    }
}

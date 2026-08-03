package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.entity.Notificacion;
import com.trainer.trainer_booking_api.repository.NotificacionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    private final NotificacionRepository notificacionRepository;

    public NotificacionController(NotificacionRepository notificacionRepository) {
        this.notificacionRepository = notificacionRepository;
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Notificacion> obtenerPorUsuario(@PathVariable Integer idUsuario) {
        return notificacionRepository.findByUsuarioIdUsuarioOrderByFechaDesc(idUsuario);
    }
}

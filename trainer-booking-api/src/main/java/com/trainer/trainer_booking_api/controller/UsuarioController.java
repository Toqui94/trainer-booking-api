package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.response.UsuarioResponseDTO;
import com.trainer.trainer_booking_api.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioService.obtenerTodos();
    }

    @GetMapping("/{id}")
    public UsuarioResponseDTO obtenerPorId(@PathVariable Integer id) {
        return usuarioService.obtenerPorId(id);
    }

    @GetMapping("/correo/{correo}")
    public UsuarioResponseDTO obtenerPorCorreo(@PathVariable String correo) {
        return usuarioService.obtenerPorCorreo(correo);
    }
}
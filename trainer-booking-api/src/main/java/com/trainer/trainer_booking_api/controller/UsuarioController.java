package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.entity.Usuario;
import com.trainer.trainer_booking_api.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // GET /api/usuarios -> Devuelve TODOS los usuarios
    @GetMapping
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    // GET /api/usuarios/1 -> Devuelve el usuario con ID 1
    @GetMapping("/{id}")
    public Usuario obtenerPorId(@PathVariable Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    // GET /api/usuarios/correo/juan@email.com -> Busca por correo (método mágico del Repository)
    @GetMapping("/correo/{correo}")
    public Usuario obtenerPorCorreo(@PathVariable String correo) {
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con correo: " + correo));
    }
}
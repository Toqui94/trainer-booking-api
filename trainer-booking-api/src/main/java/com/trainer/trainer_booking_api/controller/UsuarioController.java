package com.trainer.trainer_booking_api.controller;

import com.trainer.trainer_booking_api.dto.request.UsuarioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.UsuarioResponseDTO;
import com.trainer.trainer_booking_api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // ========== READ (Ya lo tenías) ==========
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

    // ========== CREATE (NUEVO) ==========
    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> crearUsuario(@Valid @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO creado = usuarioService.crearUsuario(dto);
        
        // ResponseEntity.status(201) = "Created". Es el código HTTP correcto para creaciones.
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    // ========== UPDATE (NUEVO) ==========
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        
        UsuarioResponseDTO actualizado = usuarioService.actualizarUsuario(id, dto);
        return ResponseEntity.ok(actualizado); // 200 OK
    }

    // ========== DELETE (NUEVO) ==========
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        usuarioService.eliminarUsuario(id);
        
        // 204 No Content = "Se borró exitosamente, no hay nada que mostrar"
        return ResponseEntity.noContent().build();
    }
}
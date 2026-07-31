package com.trainer.trainer_booking_api.controller;
import com.trainer.trainer_booking_api.dto.request.LoginRequestDTO;
import com.trainer.trainer_booking_api.config.JwtUtil;
import com.trainer.trainer_booking_api.dto.request.UsuarioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.UsuarioResponseDTO;
import com.trainer.trainer_booking_api.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioService usuarioService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    // ========== REGISTRO ==========
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO creado = usuarioService.crearUsuario(dto);
        return ResponseEntity.ok(creado);
    }

    // ========== LOGIN ==========
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDTO dto) {
        // 1. Intentar autenticar con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getCorreo(), dto.getPassword())
        );

        // 2. Si llegamos aquí, el usuario y password son correctos
        // Generamos el JWT
        String token = jwtUtil.generateToken(dto.getCorreo());

        // 3. Devolvemos el token al cliente
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("tipo", "Bearer");

        return ResponseEntity.ok(response);
    }
}
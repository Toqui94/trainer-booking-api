package com.trainer.trainer_booking_api.controller;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainer.trainer_booking_api.config.JwtUtil;
import com.trainer.trainer_booking_api.dto.request.LoginRequestDTO;
import com.trainer.trainer_booking_api.dto.request.UsuarioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.UsuarioResponseDTO;
import com.trainer.trainer_booking_api.entity.Usuario;
import com.trainer.trainer_booking_api.repository.UsuarioRepository;
import com.trainer.trainer_booking_api.service.UsuarioService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;   

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          UsuarioService usuarioService,
                          UsuarioRepository usuarioRepository) {   
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
        this.usuarioRepository = usuarioRepository;  
    }

    // ========== REGISTRO ==========
    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO creado = usuarioService.crearUsuario(dto);
        return ResponseEntity.ok(creado);
    }

    // ========== LOGIN ==========
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO dto) {
        // 1. Intentar autenticar con Spring Security
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getCorreo(), dto.getPassword())
        );

        // 2. Si llegamos aquí, el usuario y password son correctos
        // Generamos el JWT
        Usuario usuario = usuarioRepository.findByCorreo(dto.getCorreo()).orElseThrow();
        List<String> roles = usuario.getRoles().stream()
                .map(rol -> rol.getNombreRol())
                .collect(Collectors.toList());
        String token = jwtUtil.generateToken(dto.getCorreo(), roles);

        // NUEVO: armamos la info del usuario que el frontend necesita para redirigir por rol
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id_usuario", usuario.getIdUsuario());
        userInfo.put("nombre", usuario.getNombre());
        userInfo.put("apellido", usuario.getApellido());
        userInfo.put("correo", usuario.getCorreo());
        userInfo.put("roles", roles);

        // 3. Devolvemos el token al cliente
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("tipo", "Bearer");
        response.put("user", userInfo);

        return ResponseEntity.ok(response);
    }
}
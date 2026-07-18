package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.response.RolResponseDTO;
import com.trainer.trainer_booking_api.entity.Rol;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.RolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service  // Le dice a Spring: "Esta clase es un Service, gestiónala por mí"
public class RolService {

    private final RolRepository rolRepository;

    // Inyección de dependencias
    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    // Convertir Entity a DTO (método privado, solo uso interno)
    private RolResponseDTO convertirADTO(Rol rol) {
        return new RolResponseDTO(
                rol.getIdRol(),
                rol.getNombreRol(),
                rol.getDescripcion()
        );
    }

    // Obtener todos los roles como DTOs
    public List<RolResponseDTO> obtenerTodos() {
        List<Rol> roles = rolRepository.findAll();
        
        // Convertimos cada Rol (Entity) en RolResponseDTO
        return roles.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Obtener un rol por ID
    public RolResponseDTO obtenerPorId(Integer id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Rol no encontrado con id: " + id));
        
        return convertirADTO(rol);
    }
}
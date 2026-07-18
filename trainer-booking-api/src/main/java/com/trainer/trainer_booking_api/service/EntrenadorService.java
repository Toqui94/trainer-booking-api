package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.response.EntrenadorResponseDTO;
import com.trainer.trainer_booking_api.entity.Entrenador;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.EntrenadorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;

    public EntrenadorService(EntrenadorRepository entrenadorRepository) {
        this.entrenadorRepository = entrenadorRepository;
    }

    private EntrenadorResponseDTO convertirADTO(Entrenador entrenador) {
        return new EntrenadorResponseDTO(
                entrenador.getIdEntrenador(),
                entrenador.getDocumento(),
                entrenador.getDescripcion(),
                entrenador.getAniosExperiencia(),
                entrenador.getFoto(),
                entrenador.getCiudad(),
                entrenador.getEstadoVerificacion().name(),
                entrenador.getCalificacion(),
                entrenador.getIdiomas()
        );
    }

    public List<EntrenadorResponseDTO> obtenerTodos() {
        return entrenadorRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EntrenadorResponseDTO obtenerPorId(Integer id) {
        Entrenador entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Entrenador no encontrado con id: " + id));
        
        return convertirADTO(entrenador);
    }

    public List<EntrenadorResponseDTO> obtenerPorCiudad(String ciudad) {
        return entrenadorRepository.findByCiudad(ciudad)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<EntrenadorResponseDTO> obtenerPorEstadoVerificacion(String estado) {
        return entrenadorRepository.findByEstadoVerificacion(estado)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
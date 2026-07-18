package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.response.EspecialidadResponseDTO;
import com.trainer.trainer_booking_api.entity.Especialidad;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EspecialidadService {

    private final EspecialidadRepository especialidadRepository;

    public EspecialidadService(EspecialidadRepository especialidadRepository) {
        this.especialidadRepository = especialidadRepository;
    }

    private EspecialidadResponseDTO convertirADTO(Especialidad esp) {
        return new EspecialidadResponseDTO(
                esp.getIdEspecialidad(),
                esp.getNombre(),
                esp.getDescripcion()
        );
    }

    public List<EspecialidadResponseDTO> obtenerTodas() {
        return especialidadRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public EspecialidadResponseDTO obtenerPorId(Integer id) {
        Especialidad esp = especialidadRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Especialidad no encontrada con id: " + id));
        
        return convertirADTO(esp);
    }
}
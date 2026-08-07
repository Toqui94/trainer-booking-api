package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.request.HorarioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.HorarioResponseDTO;
import com.trainer.trainer_booking_api.entity.Entrenador;
import com.trainer.trainer_booking_api.entity.Horario;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.EntrenadorRepository;
import com.trainer.trainer_booking_api.repository.HorarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final EntrenadorRepository entrenadorRepository;

    public HorarioService(HorarioRepository horarioRepository, EntrenadorRepository entrenadorRepository) {
        this.horarioRepository = horarioRepository;
        this.entrenadorRepository = entrenadorRepository;
    }

    private HorarioResponseDTO convertirADTO(Horario horario) {
        return new HorarioResponseDTO(
                horario.getIdHorario(),
                horario.getEntrenador().getIdEntrenador(),
                horario.getDia(),
                horario.getHoraInicio(),
                horario.getHoraFin(),
                horario.getDisponible()
        );
    }

    public List<HorarioResponseDTO> obtenerPorEntrenador(Integer idEntrenador) {
        return horarioRepository.findByEntrenadorIdEntrenadorAndDisponibleTrue(idEntrenador)
                .stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    public HorarioResponseDTO crearHorario(HorarioRequestDTO dto) {
        Entrenador entrenador = entrenadorRepository.findById(dto.getIdEntrenador())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un entrenador con id: " + dto.getIdEntrenador()));

        if (!dto.getHoraInicio().isBefore(dto.getHoraFin())) {
            throw new RuntimeException("La hora de inicio debe ser anterior a la hora de fin");
        }

        Horario horario = new Horario();
        horario.setEntrenador(entrenador);
        horario.setDia(dto.getDia().toUpperCase());
        horario.setHoraInicio(dto.getHoraInicio());
        horario.setHoraFin(dto.getHoraFin());
        horario.setDisponible(true);

        return convertirADTO(horarioRepository.save(horario));
    }

    public void eliminarHorario(Integer id) {
        if (!horarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Horario no encontrado con id: " + id);
        }
        horarioRepository.deleteById(id);
    }
}
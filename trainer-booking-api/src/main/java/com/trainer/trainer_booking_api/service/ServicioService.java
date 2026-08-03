package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.request.ServicioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.ServicioResponseDTO;
import com.trainer.trainer_booking_api.entity.Entrenador;
import com.trainer.trainer_booking_api.entity.Servicio;
import com.trainer.trainer_booking_api.entity.enums.Modalidad;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.EntrenadorRepository;
import com.trainer.trainer_booking_api.repository.ServicioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;
    private final EntrenadorRepository entrenadorRepository;

    public ServicioService(ServicioRepository servicioRepository,
                           EntrenadorRepository entrenadorRepository) {
        this.servicioRepository = servicioRepository;
        this.entrenadorRepository = entrenadorRepository;
    }

    // ========== MÉTODO PRIVADO: Convertir Entity a ResponseDTO ==========
    private ServicioResponseDTO convertirADTO(Servicio servicio) {
        return new ServicioResponseDTO(
                servicio.getIdServicio(),
                servicio.getEntrenador().getIdEntrenador(),
                servicio.getEntrenador().getUsuario().getNombre() + " " + servicio.getEntrenador().getUsuario().getApellido(),
                servicio.getNombreServicio(),
                servicio.getDescripcion(),
                servicio.getModalidad().name(),
                servicio.getDuracion(),
                servicio.getPrecio(),
                servicio.getEstado()
        );
    }

    // ========== MÉTODO PRIVADO: Convertir String a Modalidad con mensaje claro ==========
    private Modalidad parsearModalidad(String valor) {
        try {
            return Modalidad.valueOf(valor.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Modalidad inválida: '" + valor + "'. Valores permitidos: PRESENCIAL, VIRTUAL, HIBRIDO");
        }
    }

    // ========== READ ==========
    public List<ServicioResponseDTO> obtenerTodos() {
        return servicioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ServicioResponseDTO obtenerPorId(Integer id) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con id: " + id));
        return convertirADTO(servicio);
    }

    public List<ServicioResponseDTO> obtenerPorEntrenador(Integer idEntrenador) {
        return servicioRepository.findByEntrenador_IdEntrenador(idEntrenador)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ========== CREATE ==========
    public ServicioResponseDTO crearServicio(ServicioRequestDTO dto) {
        // VALIDACIÓN: ¿existe el entrenador?
        Entrenador entrenador = entrenadorRepository.findById(dto.getIdEntrenador())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un entrenador con id: " + dto.getIdEntrenador()));

        Modalidad modalidad = parsearModalidad(dto.getModalidad());

        Servicio servicio = new Servicio();
        servicio.setEntrenador(entrenador);
        servicio.setNombreServicio(dto.getNombreServicio());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setModalidad(modalidad);
        servicio.setDuracion(dto.getDuracion());
        servicio.setPrecio(dto.getPrecio());
        servicio.setEstado(true);

        Servicio guardado = servicioRepository.save(servicio);
        return convertirADTO(guardado);
    }

    // ========== UPDATE ==========
    public ServicioResponseDTO actualizarServicio(Integer id, ServicioRequestDTO dto) {
        Servicio servicio = servicioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con id: " + id));

        // Si cambia el entrenador, validamos que el nuevo exista
        if (!servicio.getEntrenador().getIdEntrenador().equals(dto.getIdEntrenador())) {
            Entrenador nuevoEntrenador = entrenadorRepository.findById(dto.getIdEntrenador())
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe un entrenador con id: " + dto.getIdEntrenador()));
            servicio.setEntrenador(nuevoEntrenador);
        }

        servicio.setNombreServicio(dto.getNombreServicio());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setModalidad(parsearModalidad(dto.getModalidad()));
        servicio.setDuracion(dto.getDuracion());
        servicio.setPrecio(dto.getPrecio());

        Servicio actualizado = servicioRepository.save(servicio);
        return convertirADTO(actualizado);
    }

    // ========== DELETE ==========
    public void eliminarServicio(Integer id) {
        if (!servicioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Servicio no encontrado con id: " + id);
        }
        servicioRepository.deleteById(id);
    }
}
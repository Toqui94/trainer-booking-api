package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.request.EntrenadorRequestDTO;
import com.trainer.trainer_booking_api.dto.response.EntrenadorResponseDTO;
import com.trainer.trainer_booking_api.entity.Entrenador;
import com.trainer.trainer_booking_api.entity.Usuario;
import com.trainer.trainer_booking_api.entity.enums.EstadoVerificacion;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.EntrenadorRepository;
import com.trainer.trainer_booking_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntrenadorService {

    private final EntrenadorRepository entrenadorRepository;
    private final UsuarioRepository usuarioRepository;

    // Inyectamos AMBOS repositories porque necesitamos validar que el usuario exista
    public EntrenadorService(EntrenadorRepository entrenadorRepository, 
                             UsuarioRepository usuarioRepository) {
        this.entrenadorRepository = entrenadorRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // ========== MÉTODO PRIVADO: Convertir Entity a ResponseDTO ==========
    private EntrenadorResponseDTO convertirADTO(Entrenador entrenador) {
        return new EntrenadorResponseDTO(
                entrenador.getIdEntrenador(),
                entrenador.getUsuario().getNombre() + " " + entrenador.getUsuario().getApellido(), // NUEVO
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

    // ========== READ  ==========
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

    // ========== CREATE (NUEVO) ==========
    public EntrenadorResponseDTO crearEntrenador(EntrenadorRequestDTO dto) {
        // VALIDACIÓN DE NEGOCIO: ¿Existe el usuario? Y de paso, lo obtenemos.
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con id: " + dto.getIdUsuario()));

        // VALIDACIÓN DE NEGOCIO: ¿Ese usuario YA es entrenador?
        if (entrenadorRepository.findByUsuario_IdUsuario(dto.getIdUsuario()).isPresent()) {
            throw new RuntimeException("El usuario con id " + dto.getIdUsuario() + " ya tiene perfil de entrenador");
        }

        // Creamos la entidad desde cero
        Entrenador entrenador = new Entrenador();
        entrenador.setUsuario(usuario);   // ← ahora se asigna el OBJETO, no el id
        entrenador.setDocumento(dto.getDocumento());
        entrenador.setDescripcion(dto.getDescripcion());
        entrenador.setAniosExperiencia(dto.getAniosExperiencia());
        entrenador.setFoto(dto.getFoto());
        entrenador.setCiudad(dto.getCiudad());
        entrenador.setIdiomas(dto.getIdiomas());
        entrenador.setEstadoVerificacion(EstadoVerificacion.PENDIENTE);

        Entrenador guardado = entrenadorRepository.save(entrenador);
        return convertirADTO(guardado);
    }

    // ========== UPDATE (NUEVO) ==========
    public EntrenadorResponseDTO actualizarEntrenador(Integer id, EntrenadorRequestDTO dto) {
        Entrenador entrenador = entrenadorRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Entrenador no encontrado con id: " + id));

        // Si quiere cambiar el usuario asociado, validamos que exista y no esté en uso
        if (!entrenador.getUsuario().getIdUsuario().equals(dto.getIdUsuario())) {
            Usuario nuevoUsuario = usuarioRepository.findById(dto.getIdUsuario())
                    .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con id: " + dto.getIdUsuario()));

            var otroEntrenador = entrenadorRepository.findByUsuario_IdUsuario(dto.getIdUsuario());
            if (otroEntrenador.isPresent() && !otroEntrenador.get().getIdEntrenador().equals(id)) {
                throw new RuntimeException("El usuario con id " + dto.getIdUsuario() + " ya tiene perfil de entrenador");
            }
            entrenador.setUsuario(nuevoUsuario);   // ← objeto, no id
        }

        entrenador.setDocumento(dto.getDocumento());
        entrenador.setDescripcion(dto.getDescripcion());
        entrenador.setAniosExperiencia(dto.getAniosExperiencia());
        entrenador.setFoto(dto.getFoto());
        entrenador.setCiudad(dto.getCiudad());
        entrenador.setIdiomas(dto.getIdiomas());

        Entrenador actualizado = entrenadorRepository.save(entrenador);
        return convertirADTO(actualizado);
    }

    // ========== DELETE  ==========
    public void eliminarEntrenador(Integer id) {
        if (!entrenadorRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Entrenador no encontrado con id: " + id);
        }
        entrenadorRepository.deleteById(id);
    }
}
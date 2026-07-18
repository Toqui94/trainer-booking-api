package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.request.UsuarioRequestDTO;
import com.trainer.trainer_booking_api.dto.response.UsuarioResponseDTO;
import com.trainer.trainer_booking_api.entity.Usuario;
import com.trainer.trainer_booking_api.entity.enums.EstadoUsuario;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ========== MÉTODO PRIVADO: Convertir Entity a ResponseDTO ==========
    private UsuarioResponseDTO convertirADTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getCorreo(),
                usuario.getTelefono(),
                usuario.getEstado().name(),
                usuario.getFechaCreacion()
        );
    }

    // ========== READ (Ya lo tenías) ==========
    public List<UsuarioResponseDTO> obtenerTodos() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO obtenerPorId(Integer id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
        return convertirADTO(usuario);
    }

    public UsuarioResponseDTO obtenerPorCorreo(String correo) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con correo: " + correo));
        return convertirADTO(usuario);
    }

    // ========== CREATE (NUEVO) ==========
    public UsuarioResponseDTO crearUsuario(UsuarioRequestDTO dto) {
        // Validación de negocio: ¿ya existe ese correo?
        if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario registrado con el correo: " + dto.getCorreo());
        }

        // Creamos la entidad desde cero
        Usuario usuario = new Usuario();
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setCorreo(dto.getCorreo());
        usuario.setTelefono(dto.getTelefono());
        usuario.setPassword(dto.getPassword()); // Por ahora en texto plano. En Fase 5 encriptamos.
        usuario.setEstado(EstadoUsuario.ACTIVO);

        // Guardamos en la base de datos
        Usuario guardado = usuarioRepository.save(usuario);

        // Devolvemos el DTO de respuesta (sin password)
        return convertirADTO(guardado);
    }

    // ========== UPDATE (NUEVO) ==========
    public UsuarioResponseDTO actualizarUsuario(Integer id, UsuarioRequestDTO dto) {
        // Buscamos el usuario existente
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));

        // Si cambia el correo, verificamos que no exista en OTRO usuario
        if (!usuario.getCorreo().equals(dto.getCorreo())) {
            if (usuarioRepository.findByCorreo(dto.getCorreo()).isPresent()) {
                throw new RuntimeException("El correo " + dto.getCorreo() + " ya está en uso por otro usuario");
            }
            usuario.setCorreo(dto.getCorreo());
        }

        // Actualizamos los campos
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setTelefono(dto.getTelefono());
        
        // Solo actualizamos password si viene algo nuevo (opcional en un PUT)
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            usuario.setPassword(dto.getPassword());
        }

        // Guardamos los cambios
        Usuario actualizado = usuarioRepository.save(usuario);
        return convertirADTO(actualizado);
    }

    // ========== DELETE (NUEVO) ==========
    public void eliminarUsuario(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }
}
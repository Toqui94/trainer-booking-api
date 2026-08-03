package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.request.ClienteRequestDTO;
import com.trainer.trainer_booking_api.dto.response.ClienteResponseDTO;
import com.trainer.trainer_booking_api.entity.Cliente;
import com.trainer.trainer_booking_api.entity.Usuario;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.ClienteRepository;
import com.trainer.trainer_booking_api.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;

    public ClienteService(ClienteRepository clienteRepository,
                          UsuarioRepository usuarioRepository) {
        this.clienteRepository = clienteRepository;
        this.usuarioRepository = usuarioRepository;
    }

    private ClienteResponseDTO convertirADTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getIdCliente(),
                cliente.getUsuario().getIdUsuario(),
                cliente.getUsuario().getNombre() + " " + cliente.getUsuario().getApellido(),
                cliente.getUsuario().getCorreo(),
                cliente.getFechaNacimiento(),
                cliente.getObjetivoFitness(),
                cliente.getNivelExperiencia(),
                cliente.getPesoKg(),
                cliente.getAlturaCm(),
                cliente.getLesiones(),
                cliente.getDireccion()
        );
    }

    public List<ClienteResponseDTO> obtenerTodos() {
        return clienteRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO obtenerPorId(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con id: " + id));
        return convertirADTO(cliente);
    }

    public ClienteResponseDTO crearCliente(ClienteRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un usuario con id: " + dto.getIdUsuario()));

        if (clienteRepository.findByUsuario_IdUsuario(dto.getIdUsuario()).isPresent()) {
            throw new RuntimeException("El usuario con id " + dto.getIdUsuario() + " ya tiene perfil de cliente");
        }

        Cliente cliente = new Cliente();
        cliente.setUsuario(usuario);
        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setObjetivoFitness(dto.getObjetivoFitness());
        cliente.setNivelExperiencia(dto.getNivelExperiencia());
        cliente.setPesoKg(dto.getPesoKg());
        cliente.setAlturaCm(dto.getAlturaCm());
        cliente.setLesiones(dto.getLesiones());
        cliente.setDireccion(dto.getDireccion());

        Cliente guardado = clienteRepository.save(cliente);
        return convertirADTO(guardado);
    }

    public ClienteResponseDTO actualizarCliente(Integer id, ClienteRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con id: " + id));

        cliente.setFechaNacimiento(dto.getFechaNacimiento());
        cliente.setObjetivoFitness(dto.getObjetivoFitness());
        cliente.setNivelExperiencia(dto.getNivelExperiencia());
        cliente.setPesoKg(dto.getPesoKg());
        cliente.setAlturaCm(dto.getAlturaCm());
        cliente.setLesiones(dto.getLesiones());
        cliente.setDireccion(dto.getDireccion());

        Cliente actualizado = clienteRepository.save(cliente);
        return convertirADTO(actualizado);
    }

    public void eliminarCliente(Integer id) {
        if (!clienteRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Cliente no encontrado con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}
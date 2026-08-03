package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.dto.request.ReservaRequestDTO;
import com.trainer.trainer_booking_api.dto.response.ReservaResponseDTO;
import com.trainer.trainer_booking_api.entity.*;
import com.trainer.trainer_booking_api.entity.enums.EstadoPago;
import com.trainer.trainer_booking_api.entity.enums.EstadoPagoEntrenador;
import com.trainer.trainer_booking_api.entity.enums.EstadoReserva;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteRepository clienteRepository;
    private final EntrenadorRepository entrenadorRepository;
    private final ServicioRepository servicioRepository;
    private final HistorialReservaRepository historialReservaRepository;
    private final PagoRepository pagoRepository;
    private final PagoEntrenadorRepository pagoEntrenadorRepository;
    private final NotificacionRepository notificacionRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(ReservaRepository reservaRepository,
                        ClienteRepository clienteRepository,
                        EntrenadorRepository entrenadorRepository,
                        ServicioRepository servicioRepository,
                        HistorialReservaRepository historialReservaRepository,
                        PagoRepository pagoRepository,
                        PagoEntrenadorRepository pagoEntrenadorRepository,
                        NotificacionRepository notificacionRepository,
                        UsuarioRepository usuarioRepository) {
        this.reservaRepository = reservaRepository;
        this.clienteRepository = clienteRepository;
        this.entrenadorRepository = entrenadorRepository;
        this.servicioRepository = servicioRepository;
        this.historialReservaRepository = historialReservaRepository;
        this.pagoRepository = pagoRepository;
        this.pagoEntrenadorRepository = pagoEntrenadorRepository;
        this.notificacionRepository = notificacionRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // ========== CONVERTIR A DTO ==========
    private ReservaResponseDTO convertirADTO(Reserva reserva) {
        return new ReservaResponseDTO(
                reserva.getIdReserva(),
                reserva.getCliente().getUsuario().getNombre() + " " + reserva.getCliente().getUsuario().getApellido(),
                reserva.getEntrenador().getUsuario().getNombre() + " " + reserva.getEntrenador().getUsuario().getApellido(),
                reserva.getServicio().getNombreServicio(),
                reserva.getFecha(),
                reserva.getHoraInicio(),
                reserva.getHoraFin(),
                reserva.getEstado().name(),
                reserva.getValor(),
                reserva.getFechaCreacion()
        );
    }

    // ========== OBTENER TODAS ==========
    public List<ReservaResponseDTO> obtenerTodas() {
        return reservaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // ========== OBTENER POR ID ==========
    public ReservaResponseDTO obtenerPorId(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con id: " + id));
        return convertirADTO(reserva);
    }

    // ========== CREAR RESERVA (El método más importante) ==========
    @Transactional  // Si algo falla, TODO se revierte (atomicidad)
    public ReservaResponseDTO crearReserva(ReservaRequestDTO dto) {
        // 1. VALIDAR: ¿Existe el cliente?
        Cliente cliente = clienteRepository.findById(dto.getIdCliente())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente no encontrado con id: " + dto.getIdCliente()));

        // 2. VALIDAR: ¿Existe el entrenador?
        Entrenador entrenador = entrenadorRepository.findById(dto.getIdEntrenador())
                .orElseThrow(() -> new RecursoNoEncontradoException("Entrenador no encontrado con id: " + dto.getIdEntrenador()));

        // 3. VALIDAR: ¿Existe el servicio?
        Servicio servicio = servicioRepository.findById(dto.getIdServicio())
                .orElseThrow(() -> new RecursoNoEncontradoException("Servicio no encontrado con id: " + dto.getIdServicio()));

        // 4. VALIDAR: ¿El servicio pertenece al entrenador?
        if (!servicio.getEntrenador().getIdEntrenador().equals(entrenador.getIdEntrenador())) {
            throw new RuntimeException("El servicio no pertenece al entrenador seleccionado");
        }

        // 5. CALCULAR hora_fin basada en la duración del servicio
        java.time.LocalTime horaFin = dto.getHoraInicio().plusMinutes(servicio.getDuracion());

        // 6. VALIDAR: ¿Choque de horarios? (El entrenador ya tiene una reserva en ese rango?)
        List<Reserva> reservasExistentes = reservaRepository.findByEntrenadorIdEntrenadorAndFechaAndEstadoNot(
                entrenador.getIdEntrenador(),
                dto.getFecha(),
                EstadoReserva.CANCELADA  // Ignoramos las canceladas
        );

        for (Reserva existente : reservasExistentes) {
            // Hay choque si: la nueva hora_inicio es antes de la hora_fin existente
            // Y la nueva hora_fin es después de la hora_inicio existente
            boolean hayChoque = dto.getHoraInicio().isBefore(existente.getHoraFin()) 
                             && horaFin.isAfter(existente.getHoraInicio());
            
            if (hayChoque) {
                throw new RuntimeException("El entrenador ya tiene una reserva entre " 
                    + existente.getHoraInicio() + " y " + existente.getHoraFin());
            }
        }

        // 7. CREAR la reserva
        Reserva reserva = new Reserva();
        reserva.setCliente(cliente);
        reserva.setEntrenador(entrenador);
        reserva.setServicio(servicio);
        reserva.setFecha(dto.getFecha());
        reserva.setHoraInicio(dto.getHoraInicio());
        reserva.setHoraFin(horaFin);
        reserva.setEstado(EstadoReserva.PENDIENTE);
        reserva.setValor(servicio.getPrecio()); // Precio automático del servicio

        Reserva guardada = reservaRepository.save(reserva);

        // 8. GUARDAR EN HISTORIAL automáticamente
        HistorialReserva historial = new HistorialReserva();
        historial.setReserva(guardada);
        historial.setEstadoAnterior(null); // Era nueva
        historial.setEstadoNuevo(EstadoReserva.PENDIENTE.name());
        historialReservaRepository.save(historial);

        // 9. CREAR PAGO DEL CLIENTE automáticamente
        Pago pago = new Pago();
        pago.setReserva(guardada);
        pago.setMonto(guardada.getValor());
        pago.setEstado(EstadoPago.PENDIENTE);
        pagoRepository.save(pago);

        // 10. CREAR PAGO AL ENTRENADOR automáticamente (comisión del 90%)
        PagoEntrenador pagoEntrenador = new PagoEntrenador();
        pagoEntrenador.setReserva(guardada);
        pagoEntrenador.setEntrenador(entrenador);
        // La plataforma se queda con 10%, el entrenador recibe 90%
        pagoEntrenador.setValor(guardada.getValor().multiply(new BigDecimal("0.90")));
        pagoEntrenador.setEstado(EstadoPagoEntrenador.PENDIENTE);
        pagoEntrenadorRepository.save(pagoEntrenador);

        // 11. NOTIFICAR al cliente
        crearNotificacion(
            cliente.getUsuario().getIdUsuario(),
            "Reserva creada",
            "Tu reserva para " + servicio.getNombreServicio() + " el " + guardada.getFecha() + " fue creada exitosamente. Estado: PENDIENTE."
        );


        return convertirADTO(guardada);
        
        
    }

    

    // ========== CONFIRMAR RESERVA ==========
    @Transactional
    public ReservaResponseDTO confirmarReserva(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con id: " + id));

        if (reserva.getEstado() != EstadoReserva.PENDIENTE) {
            throw new RuntimeException("Solo se pueden confirmar reservas en estado PENDIENTE");
        }

        String estadoAnterior = reserva.getEstado().name();
        reserva.setEstado(EstadoReserva.CONFIRMADA);
        Reserva actualizada = reservaRepository.save(reserva);

        // Guardar historial
        HistorialReserva historial = new HistorialReserva();
        historial.setReserva(actualizada);
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(EstadoReserva.CONFIRMADA.name());
        historialReservaRepository.save(historial);

        // Notificar al cliente
        crearNotificacion(
            reserva.getCliente().getUsuario().getIdUsuario(),
            "Reserva confirmada",
            "Tu reserva para " + reserva.getServicio().getNombreServicio() + " el " + reserva.getFecha() + " fue confirmada."
        );

        return convertirADTO(actualizada);
    }

    // ========== CANCELAR RESERVA ==========
    @Transactional
    public ReservaResponseDTO cancelarReserva(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con id: " + id));

        if (reserva.getEstado() == EstadoReserva.REALIZADA || reserva.getEstado() == EstadoReserva.CANCELADA) {
            throw new RuntimeException("No se puede cancelar una reserva ya realizada o cancelada");
        }

        String estadoAnterior = reserva.getEstado().name();
        reserva.setEstado(EstadoReserva.CANCELADA);
        Reserva actualizada = reservaRepository.save(reserva);

        // Guardar historial
        HistorialReserva historial = new HistorialReserva();
        historial.setReserva(actualizada);
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(EstadoReserva.CANCELADA.name());
        historialReservaRepository.save(historial);

        // Notificar al cliente
        crearNotificacion(
            reserva.getCliente().getUsuario().getIdUsuario(),
            "Reserva cancelada",
            "Tu reserva para " + reserva.getServicio().getNombreServicio() + " el " + reserva.getFecha() + " fue cancelada."
        );

        return convertirADTO(actualizada);
    }

    // ========== MARCAR COMO REALIZADA ==========
    @Transactional
    public ReservaResponseDTO marcarRealizada(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada con id: " + id));

        if (reserva.getEstado() != EstadoReserva.CONFIRMADA) {
            throw new RuntimeException("Solo se pueden marcar como realizadas las reservas confirmadas");
        }

        String estadoAnterior = reserva.getEstado().name();
        reserva.setEstado(EstadoReserva.REALIZADA);
        Reserva actualizada = reservaRepository.save(reserva);

        HistorialReserva historial = new HistorialReserva();
        historial.setReserva(actualizada);
        historial.setEstadoAnterior(estadoAnterior);
        historial.setEstadoNuevo(EstadoReserva.REALIZADA.name());
        historialReservaRepository.save(historial);

        // Notificar al cliente que ya puede calificar
        crearNotificacion(
            reserva.getCliente().getUsuario().getIdUsuario(),
            "¡Sesión completada!",
            "Tu sesión con " + reserva.getEntrenador().getUsuario().getNombre() + " fue realizada. Ya puedes dejar tu calificación."
        );

        return convertirADTO(actualizada);
    }

    private void crearNotificacion(Integer idUsuario, String titulo, String mensaje) {
    Usuario usuario = usuarioRepository.findById(idUsuario)
            .orElse(null); // Si no existe, simplemente no creamos notificación
    
    if (usuario != null) {
        Notificacion notificacion = new Notificacion();
        notificacion.setUsuario(usuario);
        notificacion.setTitulo(titulo);
        notificacion.setMensaje(mensaje);
        notificacionRepository.save(notificacion);
    }
}
}
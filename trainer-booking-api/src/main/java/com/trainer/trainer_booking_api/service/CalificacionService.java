package com.trainer.trainer_booking_api.service;

import com.trainer.trainer_booking_api.entity.Calificacion;
import com.trainer.trainer_booking_api.entity.Reserva;
import com.trainer.trainer_booking_api.entity.enums.EstadoReserva;
import com.trainer.trainer_booking_api.exception.RecursoNoEncontradoException;
import com.trainer.trainer_booking_api.repository.CalificacionRepository;
import com.trainer.trainer_booking_api.repository.ReservaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CalificacionService {

    private final CalificacionRepository calificacionRepository;
    private final ReservaRepository reservaRepository;

    public CalificacionService(CalificacionRepository calificacionRepository,
                               ReservaRepository reservaRepository) {
        this.calificacionRepository = calificacionRepository;
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Calificacion calificar(Integer idReserva, Integer puntuacion, String comentario) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new RecursoNoEncontradoException("Reserva no encontrada"));

        // VALIDACIÓN: Solo se puede calificar si la reserva está REALIZADA
        if (reserva.getEstado() != EstadoReserva.REALIZADA) {
            throw new RuntimeException("Solo se pueden calificar reservas realizadas");
        }

        // VALIDACIÓN: No calificar dos veces
        if (calificacionRepository.findByReservaIdReserva(idReserva).isPresent()) {
            throw new RuntimeException("Esta reserva ya fue calificada");
        }

        Calificacion calificacion = new Calificacion();
        calificacion.setReserva(reserva);
        calificacion.setCliente(reserva.getCliente());
        calificacion.setEntrenador(reserva.getEntrenador());
        calificacion.setPuntuacion(puntuacion);
        calificacion.setComentario(comentario);

        return calificacionRepository.save(calificacion);
    }
}

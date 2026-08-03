package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Reserva;
import com.trainer.trainer_booking_api.entity.enums.EstadoReserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByClienteIdCliente(Integer idCliente);
    List<Reserva> findByEntrenadorIdEntrenador(Integer idEntrenador);
    List<Reserva> findByFechaAndEntrenadorIdEntrenador(LocalDate fecha, Integer idEntrenador);
    // Verificar si hay choque de horario para un entrenador en una fecha
    List<Reserva> findByEntrenadorIdEntrenadorAndFechaAndEstadoNot(Integer idEntrenador, LocalDate fecha, EstadoReserva estado);
}

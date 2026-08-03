package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.PagoEntrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoEntrenadorRepository extends JpaRepository<PagoEntrenador, Integer> {
    Optional<PagoEntrenador> findByReservaIdReserva(Integer idReserva);
}

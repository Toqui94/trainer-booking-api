package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Calificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface CalificacionRepository extends JpaRepository<Calificacion, Integer> {
    List<Calificacion> findByEntrenadorIdEntrenador(Integer idEntrenador);
    Optional<Calificacion> findByReservaIdReserva(Integer idReserva);
}

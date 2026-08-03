package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    // Navega a través de la relación Servicio → Entrenador (mismo patrón que ya usaste)
    List<Servicio> findByEntrenador_IdEntrenador(Integer idEntrenador);
}
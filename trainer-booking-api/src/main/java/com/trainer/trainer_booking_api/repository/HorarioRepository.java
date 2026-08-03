package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HorarioRepository extends JpaRepository<Horario, Integer> {
    List<Horario> findByEntrenadorIdEntrenadorAndDisponibleTrue(Integer idEntrenador);
}

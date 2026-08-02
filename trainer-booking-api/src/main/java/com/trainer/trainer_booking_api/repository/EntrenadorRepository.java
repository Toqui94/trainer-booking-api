package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Entrenador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntrenadorRepository extends JpaRepository<Entrenador, Integer> {

    Optional<Entrenador> findByUsuario_IdUsuario(Integer idUsuario);   // ← cambiar esta línea

    List<Entrenador> findByCiudad(String ciudad);
    List<Entrenador> findByEstadoVerificacion(String estadoVerificacion);
}

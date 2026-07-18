package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    // Método mágico: Spring Data JPA crea automáticamente la consulta SQL
    // Solo con el nombre del método entiende qué quieres hacer
    Optional<Usuario> findByCorreo(String correo);
}
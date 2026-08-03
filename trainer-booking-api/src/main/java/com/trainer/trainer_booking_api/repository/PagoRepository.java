package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Integer> {
    Optional<Pago> findByReservaIdReserva(Integer idReserva);
}

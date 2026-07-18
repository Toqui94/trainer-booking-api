package com.trainer.trainer_booking_api.repository;

import com.trainer.trainer_booking_api.entity.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
}

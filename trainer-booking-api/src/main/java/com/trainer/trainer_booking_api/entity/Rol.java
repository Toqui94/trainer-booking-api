package com.trainer.trainer_booking_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity                 // Le dice a Hibernate: "Esto es una tabla"
@Table(name = "roles")  // Le dice: "En MySQL se llama 'roles'"
@Getter                 // Lombok genera automáticamente los métodos getIdRol(), getNombreRol()...
@Setter                 // Lombok genera los métodos setIdRol(), setNombreRol()...
@NoArgsConstructor      // Constructor vacío (obligatorio para JPA)
@AllArgsConstructor     // Constructor con todos los campos
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // AUTO_INCREMENT de MySQL
    @Column(name = "id_rol")
    private Integer idRol;

    @Column(name = "nombre_rol", nullable = false, length = 50)
    private String nombreRol;

    @Column(name = "descripcion", length = 255)
    private String descripcion;
}
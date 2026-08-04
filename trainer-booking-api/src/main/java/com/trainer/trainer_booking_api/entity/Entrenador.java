package com.trainer.trainer_booking_api.entity;
import java.util.List;
import com.trainer.trainer_booking_api.entity.enums.EstadoVerificacion;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "entrenadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrenador")
    private Integer idEntrenador;
    
    @OneToOne(fetch = FetchType.EAGER)  // LAZY = "no traigas el usuario hasta que lo pidan"
    @JoinColumn(name = "id_usuario", nullable = false, unique = true)
    private Usuario usuario;

    @Column(length = 30)
    private String documento;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "anios_experiencia")
    private Integer aniosExperiencia;

    @Column(length = 255)
    private String foto;

    @Column(length = 50)
    private String ciudad;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_verificacion", nullable = false, length = 20)
    private EstadoVerificacion estadoVerificacion = EstadoVerificacion.PENDIENTE;

    @Column(precision = 3, scale = 1)
    private BigDecimal calificacion;

    @Column(name = "tarifa_base", precision = 10, scale = 2)
    private BigDecimal tarifaBase;

    @Column(length = 3)
    private String moneda = "USD";

    @Column(length = 100)
    private String idiomas;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToMany
    @JoinTable(
        name = "entrenador_especialidad",
        joinColumns = @JoinColumn(name = "id_entrenador"),
        inverseJoinColumns = @JoinColumn(name = "id_especialidad")
    )
    private List<Especialidad> especialidades;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}


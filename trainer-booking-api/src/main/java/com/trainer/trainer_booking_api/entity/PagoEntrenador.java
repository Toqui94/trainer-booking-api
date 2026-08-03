package com.trainer.trainer_booking_api.entity;

import com.trainer.trainer_booking_api.entity.enums.EstadoPagoEntrenador;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos_entrenadores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagoEntrenador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pago_entrenador")
    private Integer idPagoEntrenador;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false, unique = true)
    private Reserva reserva;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_entrenador", nullable = false)
    private Entrenador entrenador;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoPagoEntrenador estado = EstadoPagoEntrenador.PENDIENTE;

    @Column(name = "fecha_pago")
    private LocalDate fechaPago;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}

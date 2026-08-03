package com.trainer.trainer_booking_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HistorialReserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historial")
    private Integer idHistorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_reserva", nullable = false)
    private Reserva reserva;

    @Column(name = "estado_anterior", length = 50)
    private String estadoAnterior;

    @Column(name = "estado_nuevo", length = 50)
    private String estadoNuevo;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();
}
package com.trainer.trainer_booking_api.dto.request;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReservaRequestDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer idCliente;

    @NotNull(message = "El ID del entrenador es obligatorio")
    private Integer idEntrenador;

    @NotNull(message = "El ID del servicio es obligatorio")
    private Integer idServicio;

    @NotNull(message = "La fecha es obligatoria")
    private LocalDate fecha;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    // Constructor vacío
    public ReservaRequestDTO() {}

    // Getters y Setters
    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdEntrenador() {
        return idEntrenador;
    }

    public void setIdEntrenador(Integer idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    public Integer getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(Integer idServicio) {
        this.idServicio = idServicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }
}
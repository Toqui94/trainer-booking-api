package com.trainer.trainer_booking_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public class HorarioRequestDTO {
    @NotNull(message = "El id del entrenador es obligatorio")
    private Integer idEntrenador;

    @NotBlank(message = "El día es obligatorio (ej. LUNES)")
    private String dia;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime horaInicio;

    @NotNull(message = "La hora de fin es obligatoria")
    private LocalTime horaFin;

    public Integer getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(Integer idEntrenador) { this.idEntrenador = idEntrenador; }
    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}
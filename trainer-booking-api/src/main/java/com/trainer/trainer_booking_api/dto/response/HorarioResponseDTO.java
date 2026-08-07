package com.trainer.trainer_booking_api.dto.response;

import java.time.LocalTime;

public class HorarioResponseDTO {
    private Integer idHorario;
    private Integer idEntrenador;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private Boolean disponible;

    public HorarioResponseDTO() {}
    public HorarioResponseDTO(Integer idHorario, Integer idEntrenador, String dia, LocalTime horaInicio, LocalTime horaFin, Boolean disponible) {
        this.idHorario = idHorario; this.idEntrenador = idEntrenador; this.dia = dia;
        this.horaInicio = horaInicio; this.horaFin = horaFin; this.disponible = disponible;
    }

    public Integer getIdHorario() { return idHorario; }
    public void setIdHorario(Integer idHorario) { this.idHorario = idHorario; }
    public Integer getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(Integer idEntrenador) { this.idEntrenador = idEntrenador; }
    public String getDia() { return dia; }
    public void setDia(String dia) { this.dia = dia; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
    public Boolean getDisponible() { return disponible; }
    public void setDisponible(Boolean disponible) { this.disponible = disponible; }
}

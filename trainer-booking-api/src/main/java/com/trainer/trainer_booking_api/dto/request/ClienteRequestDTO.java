package com.trainer.trainer_booking_api.dto.request;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClienteRequestDTO {

    @NotNull(message = "El id del usuario es obligatorio")
    private Integer idUsuario;

    private LocalDate fechaNacimiento;
    private String objetivoFitness;
    private String nivelExperiencia;
    private BigDecimal pesoKg;
    private Integer alturaCm;
    private String lesiones;
    private String direccion;

    public ClienteRequestDTO() {}

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getObjetivoFitness() { return objetivoFitness; }
    public void setObjetivoFitness(String objetivoFitness) { this.objetivoFitness = objetivoFitness; }

    public String getNivelExperiencia() { return nivelExperiencia; }
    public void setNivelExperiencia(String nivelExperiencia) { this.nivelExperiencia = nivelExperiencia; }

    public BigDecimal getPesoKg() { return pesoKg; }
    public void setPesoKg(BigDecimal pesoKg) { this.pesoKg = pesoKg; }

    public Integer getAlturaCm() { return alturaCm; }
    public void setAlturaCm(Integer alturaCm) { this.alturaCm = alturaCm; }

    public String getLesiones() { return lesiones; }
    public void setLesiones(String lesiones) { this.lesiones = lesiones; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
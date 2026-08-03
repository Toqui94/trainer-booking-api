package com.trainer.trainer_booking_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ClienteResponseDTO {
    private Integer idCliente;
    private Integer idUsuario;
    private String nombreCompleto;
    private String correo;
    private LocalDate fechaNacimiento;
    private String objetivoFitness;
    private String nivelExperiencia;
    private BigDecimal pesoKg;
    private Integer alturaCm;
    private String lesiones;
    private String direccion;

    public ClienteResponseDTO() {}

    public ClienteResponseDTO(Integer idCliente, Integer idUsuario, String nombreCompleto, String correo,
                              LocalDate fechaNacimiento, String objetivoFitness, String nivelExperiencia,
                              BigDecimal pesoKg, Integer alturaCm, String lesiones, String direccion) {
        this.idCliente = idCliente;
        this.idUsuario = idUsuario;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.fechaNacimiento = fechaNacimiento;
        this.objetivoFitness = objetivoFitness;
        this.nivelExperiencia = nivelExperiencia;
        this.pesoKg = pesoKg;
        this.alturaCm = alturaCm;
        this.lesiones = lesiones;
        this.direccion = direccion;
    }

    public Integer getIdCliente() { return idCliente; }
    public void setIdCliente(Integer idCliente) { this.idCliente = idCliente; }

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

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

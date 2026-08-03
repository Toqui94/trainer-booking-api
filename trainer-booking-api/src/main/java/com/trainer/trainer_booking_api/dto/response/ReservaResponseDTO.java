package com.trainer.trainer_booking_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservaResponseDTO {
    private Integer id;
    private String clienteNombre;
    private String entrenadorNombre;
    private String servicioNombre;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String estado;
    private BigDecimal valor;
    private LocalDateTime fechaCreacion;

    public ReservaResponseDTO() {}

    public ReservaResponseDTO(Integer id, String clienteNombre, String entrenadorNombre, String servicioNombre,
                              LocalDate fecha, LocalTime horaInicio, LocalTime horaFin,
                              String estado, BigDecimal valor, LocalDateTime fechaCreacion) {
        this.id = id;
        this.clienteNombre = clienteNombre;
        this.entrenadorNombre = entrenadorNombre;
        this.servicioNombre = servicioNombre;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.valor = valor;
        this.fechaCreacion = fechaCreacion;
    }

    // Getters y Setters (genera con VS Code: clic derecho > Source Action > Generate Getters and Setters)
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getEntrenadorNombre() { return entrenadorNombre; }
    public void setEntrenadorNombre(String entrenadorNombre) { this.entrenadorNombre = entrenadorNombre; }

    public String getServicioNombre() { return servicioNombre; }
    public void setServicioNombre(String servicioNombre) { this.servicioNombre = servicioNombre; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
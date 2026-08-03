package com.trainer.trainer_booking_api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class ServicioRequestDTO {

    @NotNull(message = "El id del entrenador es obligatorio")
    private Integer idEntrenador;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombreServicio;

    private String descripcion;

    @NotBlank(message = "La modalidad es obligatoria (PRESENCIAL, VIRTUAL o HIBRIDO)")
    private String modalidad;

    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser un número positivo (en minutos)")
    private Integer duracion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un número positivo")
    private BigDecimal precio;

    public ServicioRequestDTO() {}

    public Integer getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(Integer idEntrenador) { this.idEntrenador = idEntrenador; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getModalidad() { return modalidad; }
    public void setModalidad(String modalidad) { this.modalidad = modalidad; }

    public Integer getDuracion() { return duracion; }
    public void setDuracion(Integer duracion) { this.duracion = duracion; }

    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
}
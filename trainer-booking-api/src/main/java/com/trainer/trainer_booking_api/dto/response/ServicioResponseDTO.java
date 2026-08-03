package com.trainer.trainer_booking_api.dto.response;

import java.math.BigDecimal;

public class ServicioResponseDTO {
    private Integer idServicio;
    private Integer idEntrenador;
    private String nombreEntrenador;
    private String nombreServicio;
    private String descripcion;
    private String modalidad;
    private Integer duracion;
    private BigDecimal precio;
    private Boolean estado;

    public ServicioResponseDTO() {}

    public ServicioResponseDTO(Integer idServicio, Integer idEntrenador, String nombreEntrenador,
                               String nombreServicio, String descripcion, String modalidad,
                               Integer duracion, BigDecimal precio, Boolean estado) {
        this.idServicio = idServicio;
        this.idEntrenador = idEntrenador;
        this.nombreEntrenador = nombreEntrenador;
        this.nombreServicio = nombreServicio;
        this.descripcion = descripcion;
        this.modalidad = modalidad;
        this.duracion = duracion;
        this.precio = precio;
        this.estado = estado;
    }

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }

    public Integer getIdEntrenador() { return idEntrenador; }
    public void setIdEntrenador(Integer idEntrenador) { this.idEntrenador = idEntrenador; }

    public String getNombreEntrenador() { return nombreEntrenador; }
    public void setNombreEntrenador(String nombreEntrenador) { this.nombreEntrenador = nombreEntrenador; }

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

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }
}
package com.trainer.trainer_booking_api.dto.response;

import java.math.BigDecimal;

public class EntrenadorResponseDTO {
    private Integer id;
    private String documento;
    private String descripcion;
    private Integer aniosExperiencia;
    private String foto;
    private String ciudad;
    private String estadoVerificacion;
    private BigDecimal calificacion;
    private String idiomas;

    public EntrenadorResponseDTO() {}

    public EntrenadorResponseDTO(Integer id, String documento, String descripcion, 
                                 Integer aniosExperiencia, String foto, String ciudad,
                                 String estadoVerificacion, BigDecimal calificacion, String idiomas) {
        this.id = id;
        this.documento = documento;
        this.descripcion = descripcion;
        this.aniosExperiencia = aniosExperiencia;
        this.foto = foto;
        this.ciudad = ciudad;
        this.estadoVerificacion = estadoVerificacion;
        this.calificacion = calificacion;
        this.idiomas = idiomas;
    }

    // Getters y Setters 
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getAniosExperiencia() { return aniosExperiencia; }
    public void setAniosExperiencia(Integer aniosExperiencia) { this.aniosExperiencia = aniosExperiencia; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getEstadoVerificacion() { return estadoVerificacion; }
    public void setEstadoVerificacion(String estadoVerificacion) { this.estadoVerificacion = estadoVerificacion; }

    public BigDecimal getCalificacion() { return calificacion; }
    public void setCalificacion(BigDecimal calificacion) { this.calificacion = calificacion; }

    public String getIdiomas() { return idiomas; }
    public void setIdiomas(String idiomas) { this.idiomas = idiomas; }
}
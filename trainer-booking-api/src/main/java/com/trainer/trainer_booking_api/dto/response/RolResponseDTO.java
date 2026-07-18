package com.trainer.trainer_booking_api.dto.response;

public class RolResponseDTO {
    private Integer id;
    private String nombre;
    private String descripcion;

    // Constructor vacío 
    public RolResponseDTO() {}

    // Constructor con campos
    public RolResponseDTO(Integer id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters 
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}

package com.trainer.trainer_booking_api.entity;

import com.trainer.trainer_booking_api.entity.enums.EstadoUsuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @Column(nullable = false, length = 50)
    private String nombre;

    @Column(nullable = false, length = 50)
    private String apellido;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(length = 20)
    private String telefono;

    @Column(nullable = false, length = 255)
    private String password;

    @Enumerated(EnumType.STRING)  // Guarda "ACTIVO" en vez de 0, 1, 2
    @Column(nullable = false, length = 20)
    private EstadoUsuario estado = EstadoUsuario.ACTIVO;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "email_verificado")
    private Boolean emailVerificado = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "id_usuario"),
        inverseJoinColumns = @JoinColumn(name = "id_rol")
    )
    private List<Rol> roles;
    
    // Este método se ejecuta AUTOMATICAMENTE antes de guardar por primera vez
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Este método se ejecuta AUTOMATICAMENTE antes de cada actualización
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

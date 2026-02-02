package ec.edu.ups.icc.proyectofinal.advice.models;

import java.time.LocalDateTime;


import ec.edu.ups.icc.proyectofinal.core.entities.BaseModel;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import jakarta.persistence.*;
@Entity
@Table(name = "advice")
public class AdviceEntity extends BaseModel {

    @Column(nullable = false)
    private String nombreUsuario; 
    private String telefono;

    @Column( nullable = false)
    private String mensaje; 
@Column(nullable = false)
    private String fecha;
    @Column(nullable = false)
    private String mensajeRespuesta; // Justificación del programador

    @Column(nullable = false)
    private String estado; // "pendiente", "aceptada", "negada"

   

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // RELACIONES
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UserEntity usuario; // Quien solicita

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programador_id", nullable = false)
    private UserEntity programador; // Quien atiende

    public AdviceEntity() {
        this.createdAt = LocalDateTime.now();
        this.estado = "pendiente";
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

   

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UserEntity usuario) {
        this.usuario = usuario;
    }

    public UserEntity getProgramador() {
        return programador;
    }

    public void setProgramador(UserEntity programador) {
        this.programador = programador;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }


}
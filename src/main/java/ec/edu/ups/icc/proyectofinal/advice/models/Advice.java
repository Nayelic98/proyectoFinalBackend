package ec.edu.ups.icc.proyectofinal.advice.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import ec.edu.ups.icc.proyectofinal.advice.dtos.UpdateAdviceDto;
public class Advice {

    private Long id;
    private String nombreUsuario;
    private String telefono;
    private String mensaje;
    private String mensajeRespuesta;
    private String estado;
    private String fecha;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long usuarioId;
    private Long programadorId;

    public Advice() {}

    public static Advice fromEntity(AdviceEntity entity) {
        Advice advice = new Advice();
        advice.setId(entity.getId());
        advice.setNombreUsuario(entity.getNombreUsuario());
        advice.setTelefono(entity.getTelefono());
        advice.setMensaje(entity.getMensaje());
        advice.setMensajeRespuesta(entity.getMensajeRespuesta());
        advice.setEstado(entity.getEstado());
        advice.setFecha(entity.getFecha().toString()); 
        advice.setCreatedAt(entity.getCreatedAt());
        advice.setUpdatedAt(entity.getUpdatedAt());

        if (entity.getUsuario() != null) {
            advice.setUsuarioId(entity.getUsuario().getId());
        }
        if (entity.getProgramador() != null) {
            advice.setProgramadorId(entity.getProgramador().getId());
        }
        return advice;
    }
    public AdviceEntity toEntity() {
        AdviceEntity entity = new AdviceEntity();
        if (this.id != null) {
            entity.setId(this.id);
        }
        entity.setNombreUsuario(this.nombreUsuario);
        entity.setTelefono(this.telefono);
        entity.setMensaje(this.mensaje);
        entity.setMensajeRespuesta(this.mensajeRespuesta);
        entity.setEstado(this.estado);
        if (this.fecha != null) {
            entity.setFecha(fecha);
        }
        return entity;
    }
    public Advice update(UpdateAdviceDto dto) {
        this.estado = dto.estado;
        this.mensajeRespuesta = dto.mensajeRespuesta;
        this.fecha = dto.fecha;
        return this;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }

    public String getMensajeRespuesta() { return mensajeRespuesta; }
    public void setMensajeRespuesta(String mensajeRespuesta) { this.mensajeRespuesta = mensajeRespuesta; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public Long getProgramadorId() { return programadorId; }
    public void setProgramadorId(Long programadorId) { this.programadorId = programadorId; }
}
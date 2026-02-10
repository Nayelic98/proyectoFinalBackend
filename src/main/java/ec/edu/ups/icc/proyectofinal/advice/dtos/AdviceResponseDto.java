package ec.edu.ups.icc.proyectofinal.advice.dtos;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
@JsonPropertyOrder({
    "id",
    "nombreUsuario",
    "telefono",
    "mensaje",
    "estado",
    "mensajeRespuesta",
    "fecha",
    "usuario",
    "programador",
    "createdAt"
})
public class AdviceResponseDto {

    public Long id;
    public String nombreUsuario;
    public String telefono;
    public String mensaje;
    public String estado;
    public String mensajeRespuesta;
    public String fecha;
    public LocalDateTime createdAt;
    public UserSummaryDto usuario;     
    public UserSummaryDto programador; 

    public AdviceResponseDto() {}

    public static class UserSummaryDto {
        public Long id;
        public String nombre;
        public String contacto;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }
    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public UserSummaryDto getUsuario() {
        return usuario;
    }
    public void setUsuario(UserSummaryDto usuario) {
        this.usuario = usuario;
    }
    public UserSummaryDto getProgramador() {
        return programador;
    }
    public void setProgramador(UserSummaryDto programador) {
        this.programador = programador;
    }
    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }  
}

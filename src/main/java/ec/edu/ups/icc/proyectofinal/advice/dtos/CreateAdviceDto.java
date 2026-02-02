package ec.edu.ups.icc.proyectofinal.advice.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public class CreateAdviceDto {

    @JsonProperty("nombreUsuario")
    public String nombreUsuario;

    @JsonProperty("telefono")
    public String telefono;

    @JsonProperty("mensaje")
    public String mensaje;

    @JsonProperty("usuarioId")
    public Long usuarioId;

    @JsonProperty("programadorId")
    public Long programadorId;

    @JsonProperty("fecha")
    public String fecha;

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

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Long getProgramadorId() {
        return programadorId;
    }

    public void setProgramadorId(Long programadorId) {
        this.programadorId = programadorId;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
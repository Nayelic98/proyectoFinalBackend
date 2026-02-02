package ec.edu.ups.icc.proyectofinal.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class SolicitudPostulacionDto {
    @NotBlank(message = "El nombre es obligatorio")
    public String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email inválido")
    public String email;

    @NotBlank(message = "La especialidad es obligatoria")
    public String especialidad;

    @NotBlank(message = "El portafolio es obligatorio")
    public String portafolio;

    @NotBlank(message = "La descripción es obligatoria")
    public String descripcion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getPortafolio() {
        return portafolio;
    }

    public void setPortafolio(String portafolio) {
        this.portafolio = portafolio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
}

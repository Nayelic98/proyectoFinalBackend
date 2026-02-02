package ec.edu.ups.icc.proyectofinal.user.dtos;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String nombre;
    public String password;
    @NotBlank(message = "El contacto (email) es obligatorio")
    @Email(message = "Debe ingresar un email válido")
    @Size(max = 100)
    public String contacto;

    @NotBlank(message = "El rol es obligatorio")
    public String role;

    @Size(max = 500, message = "La descripción no puede exceder los 500 caracteres")
    public String descripcion;

    public String especialidad;
    
    public String foto;
    
    public List<String> redes; // Cambiado a List<String> para recibir múltiples enlaces
    

    public CreateUserDto() {}


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getContacto() {
        return contacto;
    }


    public void setContacto(String contacto) {
        this.contacto = contacto;
    }


    public String getRole() {
        return role;
    }


    public void setRole(String role) {
        this.role = role;
    }


    public String getDescripcion() {
        return descripcion;
    }


    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }


    public String getEspecialidad() {
        return especialidad;
    }


    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }


    public String getFoto() {
        return foto;
    }


    public void setFoto(String foto) {
        this.foto = foto;
    }


    public List<String> getRedes() {
        return redes;
    }


    public void setRedes(List<String> redes) {
        this.redes = redes;
    }
}

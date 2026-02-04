package ec.edu.ups.icc.proyectofinal.project.security.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequestDto {

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    private String nombre;

    @NotBlank(message = "El contacto es obligatorio")
    @Size(max = 150, message = "El contacto no puede exceder 150 caracteres")
    // Si el contacto siempre es un email, puedes volver a añadir @Email aquí
    private String contacto;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, max = 100, message = "La contraseña debe tener entre 6 y 100 caracteres")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$", 
             message = "La contraseña debe contener al menos una mayúscula, una minúscula y un número")
    private String password;

    // Nuevos campos según tu UserEntity
    private String descripcion;
    private String foto;
    private String redes;
    private String role;

    // Constructores
    public RegisterRequestDto() {
    }

    public RegisterRequestDto(String nombre, String contacto, String password) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.password = password;
    }

    // Getters y Setters corregidos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getRedes() {
        return redes;
    }

    public void setRedes(String redes) {
        this.redes = redes;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
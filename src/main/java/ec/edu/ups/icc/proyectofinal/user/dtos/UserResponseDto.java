package ec.edu.ups.icc.proyectofinal.user.dtos;

import java.util.List;

public class UserResponseDto {
    public Long id;
    public String nombre;
    public String contacto;
    public String especialidad; // 👈 Debe existir
    public String descripcion;  // 👈 Debe existir
    public String foto;  
    public List<String> redes;       // 👈 Debe existir
    public String role;
    public boolean mustChangePassword;
    public UserResponseDto() {
    }
    
    
    public UserResponseDto(String nombre, String contacto, String especialidad, String descripcion, String foto,
            List<String> redes, String role, boolean mustChangePassword) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.especialidad = especialidad;
        this.descripcion = descripcion;
        this.foto = foto;
        this.redes = redes;
        this.role = role;
        this.mustChangePassword = mustChangePassword;
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
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
    public String getEspecialidad() {
        return especialidad;
    }
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
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
    
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public boolean isMustChangePassword() {
        return mustChangePassword;
    }
    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }


    public void setRedes(List<String> redes) {
        this.redes = redes;
    }
    
}

package ec.edu.ups.icc.proyectofinal.project.security.dtos;

import jakarta.validation.constraints.NotBlank;
public class GoogleLoginRequestDto {
    @NotBlank(message = "El contacto (email) es obligatorio")
    private String contacto;
    
    private String nombre;
    private String foto; 

    public GoogleLoginRequestDto() {}

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }
}
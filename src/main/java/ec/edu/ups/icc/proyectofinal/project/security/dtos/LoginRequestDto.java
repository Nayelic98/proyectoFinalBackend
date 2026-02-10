package ec.edu.ups.icc.proyectofinal.project.security.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
public class LoginRequestDto {
    @NotBlank(message = "El email es obligatorio")
    @NotBlank(message = "El contacto es obligatorio")
    private String contacto; 
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    public LoginRequestDto() {
    }
    public LoginRequestDto(String contacto, String password) {
        this.contacto = contacto;
        this.password = password;
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
}

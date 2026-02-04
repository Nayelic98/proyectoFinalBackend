package ec.edu.ups.icc.proyectofinal.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdateUserDto {
   @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 150)
    public String nombre;

    @NotBlank(message = "El contacto no puede estar vacío")
    @Email(message = "Email inválido")
    public String contacto;

    @NotBlank(message = "La descripción es necesaria para el perfil")
    public String descripcion;

    public String especialidad;
    
    public String foto;
    
    public String redes;

    public UpdateUserDto() {}
}
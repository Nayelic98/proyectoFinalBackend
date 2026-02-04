package ec.edu.ups.icc.proyectofinal.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserDto {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 3, max = 150, message = "El nombre debe tener entre 3 y 150 caracteres")
    public String nombre;

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
    
    public String redes;

    public CreateUserDto() {}
}

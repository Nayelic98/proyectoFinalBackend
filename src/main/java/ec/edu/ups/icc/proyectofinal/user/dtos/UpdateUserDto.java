package ec.edu.ups.icc.proyectofinal.user.dtos;

import java.util.List;

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
    
    public String foto; // Aquí recibiremos el Base64 desde Angular
    
    // Cambiamos String a List<String> para recibir múltiples enlaces
    public List<String> redes; 

    public UpdateUserDto() {}

}
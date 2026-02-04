package ec.edu.ups.icc.proyectofinal.project.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateProjectDto {
@NotBlank(message = "El nombre del proyecto es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    public String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(min = 10, message = "La descripción debe ser más detallada")
    public String descripcion;

    @NotBlank(message = "La categoría es obligatoria")
    public String categoria; 

    @NotBlank(message = "El tipo de proyecto es obligatorio")
    public String tipo;

    public String deploy; // URL opcional
    
    public String repo;   // URL opcional

    @NotEmpty(message = "Debe incluir al menos una tecnología")
    public List<String> tecnologias;

    @NotNull(message = "El ID del programador es obligatorio")
    public Long assignedToId;

    public CreateProjectDto() {}
}

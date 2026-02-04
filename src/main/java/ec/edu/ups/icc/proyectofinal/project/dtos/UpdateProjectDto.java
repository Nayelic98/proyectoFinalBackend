package ec.edu.ups.icc.proyectofinal.project.dtos;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class UpdateProjectDto {
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 100)
    public String nombre;

    @NotBlank(message = "La descripción no puede estar vacía")
    public String descripcion;

    @NotBlank(message = "La categoría es obligatoria")
    public String categoria;

    @NotBlank(message = "El tipo es obligatorio")
    public String tipo;

    public String deploy;
    
    public String repo;

    @NotEmpty(message = "La lista de tecnologías no puede estar vacía")
    public List<String> tecnologias;

    public UpdateProjectDto() {}
}

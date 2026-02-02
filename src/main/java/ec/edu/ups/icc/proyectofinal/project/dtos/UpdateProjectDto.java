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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDeploy() {
        return deploy;
    }

    public void setDeploy(String deploy) {
        this.deploy = deploy;
    }

    public String getRepo() {
        return repo;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public List<String> getTecnologias() {
        return tecnologias;
    }

    public void setTecnologias(List<String> tecnologias) {
        this.tecnologias = tecnologias;
    }
}

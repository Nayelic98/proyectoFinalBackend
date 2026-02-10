package ec.edu.ups.icc.proyectofinal.project.dtos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
    @JsonPropertyOrder({
    "id",
    "nombre",
    "descripcion",
    "categoria",
    "tipo",
    "deploy",
    "repo",
    "tecnologias",
    "assignedTo"
})
public class ProjectResponseDto {
    public Long id;
    public String nombre;
    public String descripcion;
    public String categoria;
    public String tipo;
    public String deploy;
    public String repo;
    public List<String> tecnologias;

    public UserSummaryDto assignedTo;
    public ProjectResponseDto() {}
    public static class UserSummaryDto {
        public Long id;
        public String nombre;
        public String contacto;
        public String especialidad;
    }
}

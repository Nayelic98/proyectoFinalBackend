package ec.edu.ups.icc.proyectofinal.project.models;

import java.util.List;
import ec.edu.ups.icc.proyectofinal.project.dtos.UpdateProjectDto;
public class Project {
    private Long id;
    private String nombre;
    private String descripcion;
    private String categoria;
    private String tipo;
    private String deploy;
    private String repo;
    private List<String> tecnologias;
    private Long assignedToId;

    public Project() {}
    public static Project fromEntity(ProjectEntity entity) {
        Project project = new Project();
        project.setId(entity.getId());
        project.setNombre(entity.getNombre());
        project.setDescripcion(entity.getDescripcion());
        project.setCategoria(entity.getCategoria());
        project.setTipo(entity.getTipo());
        project.setDeploy(entity.getDeploy());
        project.setRepo(entity.getRepo());
        project.setTecnologias(entity.getTecnologias());
        
        if (entity.getAssignedTo() != null) {
            project.setAssignedToId(entity.getAssignedTo().getId());
        }  
        return project;
    }
    public ProjectEntity toEntity() {
        ProjectEntity entity = new ProjectEntity();
        if (this.id != null) {
            entity.setId(this.id);
        }
        entity.setNombre(this.nombre);
        entity.setDescripcion(this.descripcion);
        entity.setCategoria(this.categoria);
        entity.setTipo(this.tipo);
        entity.setDeploy(this.deploy);
        entity.setRepo(this.repo);
        entity.setTecnologias(this.tecnologias);
        return entity;
    }
    public Project update(UpdateProjectDto dto) {
        this.nombre = dto.nombre;
        this.descripcion = dto.descripcion;
        this.categoria = dto.categoria;
        this.tipo = dto.tipo;
        this.deploy = dto.deploy;
        this.repo = dto.repo;
        this.tecnologias = dto.tecnologias;
        return this;
    }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDeploy() { return deploy; }
    public void setDeploy(String deploy) { this.deploy = deploy; }

    public String getRepo() { return repo; }
    public void setRepo(String repo) { this.repo = repo; }

    public List<String> getTecnologias() { return tecnologias; }
    public void setTecnologias(List<String> tecnologias) { this.tecnologias = tecnologias; }

    public Long getAssignedToId() { return assignedToId; }
    public void setAssignedToId(Long assignedToId) { this.assignedToId = assignedToId; }
}
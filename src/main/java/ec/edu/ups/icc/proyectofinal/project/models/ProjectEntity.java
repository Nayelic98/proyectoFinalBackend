package ec.edu.ups.icc.proyectofinal.project.models;

import ec.edu.ups.icc.proyectofinal.core.entities.BaseModel;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "projects")
public class ProjectEntity extends BaseModel {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 50)
    private String categoria;

    @Column(length = 50)
    private String tipo;

    private String deploy;
    private String repo;

    @ElementCollection
    @CollectionTable(name = "project_tecnologias", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "tecnologia")
    private List<String> tecnologias = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to", nullable = false)
    private UserEntity assignedTo;

    public ProjectEntity() {}
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
    public UserEntity getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(UserEntity assignedTo) {
        this.assignedTo = assignedTo;
    }
}
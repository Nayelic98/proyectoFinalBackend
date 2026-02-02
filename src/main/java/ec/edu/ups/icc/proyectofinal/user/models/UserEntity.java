package ec.edu.ups.icc.proyectofinal.user.models;

import ec.edu.ups.icc.proyectofinal.core.entities.BaseModel;
import ec.edu.ups.icc.proyectofinal.project.models.ProjectEntity;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;





@Entity
@Table(name = "users")
public class UserEntity extends BaseModel {

    @Column(nullable = false, length = 150)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String contacto;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 50)
    private String especialidad;

    @Column(nullable = false)
    private String foto;

   @ElementCollection
@CollectionTable(name = "user_redes", joinColumns = @JoinColumn(name = "user_id"))
@Column(name = "url")
private List<String> redes = new ArrayList<>(); // Inicializar siempre para evitar NullPointerException

    

    @Column(nullable = false)
    private boolean mustChangePassword;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private String password; // o el nombre que uses para la clave

    // Setter manual (si no usas Lombok)
    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    private String createdBy; // ID del admin que lo creó

    // RELACIONES
    @OneToMany(mappedBy = "assignedTo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProjectEntity> projects = new ArrayList<>();
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();
    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
    // Constructor
    public UserEntity() {
        this.createdAt = LocalDateTime.now();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    
   




   
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public List<ProjectEntity> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectEntity> projects) {
        this.projects = projects;
    }

    public boolean isMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }

    public List<String> getRedes() {
        return redes;
    }

    public void setRedes(List<String> redes) {
        this.redes = redes;
    }

    
    
}
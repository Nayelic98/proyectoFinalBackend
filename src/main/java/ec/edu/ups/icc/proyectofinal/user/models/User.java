package ec.edu.ups.icc.proyectofinal.user.models;

import java.time.LocalDateTime;
import java.util.List;

import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;

public class User {

    private Long id;
    private String nombre;
    private String contacto;
    private String descripcion;
    private String especialidad;
    private String foto;
    private List<String> redes;
    private String role;
    private boolean mustChangePassword;
    private LocalDateTime createdAt;

    public User(Long id, String nombre, String contacto, String descripcion, String especialidad, 
                String foto, List<String> redes, String role, boolean mustChangePassword) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.descripcion = descripcion;
        this.especialidad = especialidad;
        this.foto = foto;
        this.redes = redes; // Ahora coinciden los tipos
        this.role = role;
        this.mustChangePassword = mustChangePassword;
    }

    public User() {}

    // ==================== FACTORY METHODS ====================

    /**
     * Crea un User desde una entidad persistente
     */
    public static User fromEntity(UserEntity entity) {
        User user = new User(
            entity.getId(),
            entity.getNombre(),
            entity.getContacto(),
            entity.getDescripcion(),
            entity.getEspecialidad(),
            entity.getFoto(),
            entity.getRedes(),
            entity.getRoles().iterator().next().getName().name(),
            entity.isMustChangePassword()
        );
        user.setCreatedAt(entity.getCreatedAt());
        return user;
    }

    /**
     * Convierte este User a una entidad persistente para la BD
     */
    public UserEntity toEntity() {
        UserEntity entity = new UserEntity();
        if (this.id != null && this.id > 0) {
            entity.setId(this.id);
        }
        entity.setNombre(this.nombre);
        entity.setContacto(this.contacto);
        entity.setDescripcion(this.descripcion);
        entity.setEspecialidad(this.especialidad);
        entity.setFoto(this.foto);
        entity.setRedes(this.redes);
        
        entity.setMustChangePassword(this.mustChangePassword);
        // El createdAt se maneja usualmente en el constructor de la entidad
        return entity;
    }

    // ==================== UPDATES ====================

    public User update(UpdateUserDto dto) {
        if (dto.nombre != null) this.nombre = dto.nombre;
        if (dto.contacto != null) this.contacto = dto.contacto;
        if (dto.descripcion != null) this.descripcion = dto.descripcion;
        if (dto.especialidad != null) this.especialidad = dto.especialidad;
        if (dto.foto != null) this.foto = dto.foto;
        if (dto.redes != null) this.redes = dto.redes;
        return this;
    }

    

    // ==================== GETTERS & SETTERS ====================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getFoto() { return foto; }
    public void setFoto(String foto) { this.foto = foto; }

    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isMustChangePassword() { return mustChangePassword; }
    public void setMustChangePassword(boolean mustChangePassword) { this.mustChangePassword = mustChangePassword; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<String> getRedes() {
        return redes;
    }

    public void setRedes(List<String> redes) {
        this.redes = redes;
    }
}
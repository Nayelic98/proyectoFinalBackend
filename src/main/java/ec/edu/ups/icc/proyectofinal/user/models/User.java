package ec.edu.ups.icc.proyectofinal.user.models;

import java.time.LocalDateTime;

import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;

public class User {

    private Long id;
    private String nombre;
    private String contacto;
    private String descripcion;
    private String especialidad;
    private String foto;
    private String redes;
    private String role;
    private boolean mustChangePassword;
    private LocalDateTime createdAt;

    public User(Long id, String nombre, String contacto, String descripcion, String especialidad, 
                String foto, String redes, String role, boolean mustChangePassword) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.descripcion = descripcion;
        this.especialidad = especialidad;
        this.foto = foto;
        this.redes = redes;
        this.role = role;
        this.mustChangePassword = mustChangePassword;
        this.createdAt = LocalDateTime.now();
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
        this.nombre = dto.nombre;
        this.contacto = dto.contacto;
        this.descripcion = dto.descripcion;
        this.especialidad = dto.especialidad;
        this.foto = dto.foto;
        this.redes = dto.redes;
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

    public String getRedes() { return redes; }
    public void setRedes(String redes) { this.redes = redes; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isMustChangePassword() { return mustChangePassword; }
    public void setMustChangePassword(boolean mustChangePassword) { this.mustChangePassword = mustChangePassword; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
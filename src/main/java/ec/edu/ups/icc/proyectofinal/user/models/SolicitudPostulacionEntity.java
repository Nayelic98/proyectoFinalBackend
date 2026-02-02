package ec.edu.ups.icc.proyectofinal.user.models;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes_postulacion")
public class SolicitudPostulacionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String TemporalPassword;
    private String nombre;
    private String email;
    private String especialidad;
    private String portafolio;
    
    @Column(length = 1000)
    private String descripcion;

    private String estado = "PENDIENTE"; 
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // Debe ser exactamente así:
public SolicitudPostulacionEntity() {
}
    // Getters y Setters manuales
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    public String getPortafolio() { return portafolio; }
    public void setPortafolio(String portafolio) { this.portafolio = portafolio; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public String getTemporalPassword() {
        return TemporalPassword;
    }
    public void setTemporalPassword(String temporalPassword) {
        TemporalPassword = temporalPassword;
    }
    
}
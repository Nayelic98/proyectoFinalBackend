package ec.edu.ups.icc.proyectofinal.core.entities;
import jakarta.persistence.*;@MappedSuperclass

public abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
}

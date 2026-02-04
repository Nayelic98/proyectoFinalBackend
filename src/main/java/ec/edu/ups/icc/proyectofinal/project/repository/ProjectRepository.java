package ec.edu.ups.icc.proyectofinal.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.proyectofinal.project.models.ProjectEntity;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    List<ProjectEntity> findByAssignedToId(Long userId);

    List<ProjectEntity> findByCategoriaIgnoreCase(String categoria);

   
    List<ProjectEntity> findByTipoIgnoreCase(String tipo);

    List<ProjectEntity> findByTecnologiasContaining(String tecnologia);
    
  
    boolean existsByNombreAndAssignedToId(String nombre, Long userId);
}
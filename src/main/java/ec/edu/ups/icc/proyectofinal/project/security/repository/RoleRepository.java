package ec.edu.ups.icc.proyectofinal.project.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.edu.ups.icc.proyectofinal.project.security.models.RoleEntity;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleName;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleName name);

    boolean existsByName(RoleName name);

}

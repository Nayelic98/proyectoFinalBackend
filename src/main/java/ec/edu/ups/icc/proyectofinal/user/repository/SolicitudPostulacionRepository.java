package ec.edu.ups.icc.proyectofinal.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.proyectofinal.user.models.SolicitudPostulacionEntity;

@Repository
public interface SolicitudPostulacionRepository extends JpaRepository<SolicitudPostulacionEntity, Long> {
    Optional<SolicitudPostulacionEntity> findByEmail(String email);
        boolean existsByEmail(String email);

}

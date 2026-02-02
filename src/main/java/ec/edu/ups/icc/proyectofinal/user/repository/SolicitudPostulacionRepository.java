package ec.edu.ups.icc.proyectofinal.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.proyectofinal.user.models.SolicitudPostulacionEntity;
import jakarta.transaction.Transactional;

@Repository
public interface SolicitudPostulacionRepository extends JpaRepository<SolicitudPostulacionEntity, Long> {
    Optional<SolicitudPostulacionEntity> findByEmail(String email);
        boolean existsByEmail(String email);
        @Modifying // Necesario para DELETE o UPDATE
    @Transactional
    @Query("DELETE FROM SolicitudPostulacionEntity s WHERE s.contacto = :contacto") 
    void eliminarPorContacto(@Param("contacto") String contacto);


}

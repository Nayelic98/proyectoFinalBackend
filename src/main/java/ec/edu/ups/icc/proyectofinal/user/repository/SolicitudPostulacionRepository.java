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
   @Modifying
    @Transactional
    @Query("DELETE FROM SolicitudPostulacionEntity s WHERE s.email = :email")
    void eliminarPorEmail(@Param("email") String email);


}

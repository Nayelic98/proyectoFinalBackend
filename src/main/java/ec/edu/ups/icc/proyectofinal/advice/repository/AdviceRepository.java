package ec.edu.ups.icc.proyectofinal.advice.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ec.edu.ups.icc.proyectofinal.advice.models.AdviceEntity;
import jakarta.transaction.Transactional;

@Repository
public interface AdviceRepository extends JpaRepository<AdviceEntity, Long> {

    /**
     * Devuelve todas las asesorías que tiene un programador específico.
     * Esto permite que el programador vea quién necesita su ayuda.
     */
    @Query("SELECT a FROM AdviceEntity a WHERE a.programador.id = :programadorId")
    List<AdviceEntity> findByProgramadorId(@Param("programadorId") Long programadorId);
    @Query("SELECT a FROM AdviceEntity a WHERE a.usuario.id = :usuarioId")
    List<AdviceEntity> findByUsuarioId(@Param("usuarioId") Long usuarioId);
    @Modifying
    @Transactional
    @Query("DELETE FROM AdviceEntity a WHERE a.usuario.id = :id OR a.programador.id = :id")
    void deleteAllByUserId(@Param("id") Long id);
    /**
     * Valida si un usuario ya tiene una asesoría con un programador específico.
     * Útil para evitar duplicados o verificar historial.
     */
    boolean existsByUsuarioIdAndProgramadorId(Long usuarioId, Long programadorId);

    /**
     * Buscar asesorías por estado (ej: "pendiente") para un programador.
     */
    List<AdviceEntity> findByProgramadorIdAndEstado(Long programadorId, String estado);
}
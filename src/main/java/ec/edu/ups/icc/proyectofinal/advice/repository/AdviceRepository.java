package ec.edu.ups.icc.proyectofinal.advice.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ec.edu.ups.icc.proyectofinal.advice.models.AdviceEntity;

@Repository
public interface AdviceRepository extends JpaRepository<AdviceEntity, Long> {

    /**
     * Devuelve todas las asesorías que tiene un programador específico.
     * Esto permite que el programador vea quién necesita su ayuda.
     */
    List<AdviceEntity> findByProgramadorId(Long programadorId);

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
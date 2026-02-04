package ec.edu.ups.icc.proyectofinal.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Validar si el email ya existe (para registro o login de Google)
    Optional<UserEntity> findByContacto(String contacto);

    // Buscar programadores por nombre (ignorando mayúsculas/minúsculas)
    List<UserEntity> findByNombreContainingIgnoreCaseAndRole(String nombre, String role);

    // Verificar existencia por email
    boolean existsByContacto(String contacto);
}

package ec.edu.ups.icc.proyectofinal.project.security.init;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ec.edu.ups.icc.proyectofinal.project.security.models.RoleEntity;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleName;
import ec.edu.ups.icc.proyectofinal.project.security.repository.RoleRepository;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import ec.edu.ups.icc.proyectofinal.user.repository.UserRepository;




@Component
public class DataInitializer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Agregado
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           RoleRepository roleRepository, // Inyectado
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeRoles(); // Paso 1: Crear los roles en Neon
        createDefaultAdminUser(); // Paso 2: Crear el admin y asignarle el rol
    }

    private void initializeRoles() {
        for (RoleName name : RoleName.values()) {
            if (!roleRepository.existsByName(name)) {
                roleRepository.save(new RoleEntity(name, "Rol de sistema: " + name));
                logger.info("Rol {} creado exitosamente.", name);
            }
        }
    }

    private void createDefaultAdminUser() {
        String adminContacto = "admin@ups.edu.ec";

        if (!userRepository.existsByContacto(adminContacto)) {
            UserEntity admin = new UserEntity();
            admin.setNombre("Administrador Sistema");
            admin.setContacto(adminContacto);
            admin.setPassword(passwordEncoder.encode("admin123")); // Contraseña segura
            admin.setDescripcion("Cuenta de administración inicial");
            admin.setFoto("default-admin.png");
            admin.setRedes(List.of("https://www.linkedin.com/in/admin", "https://www.twitter.com/admin"));
            admin.setMustChangePassword(true);
            admin.setCreatedBy("SYSTEM");

            // ASIGNACIÓN DE ROL SEGÚN TU NUEVO MODELO
            RoleEntity adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Error: El rol ADMIN no existe."));
            
            admin.getRoles().add(adminRole); // Añadimos al Set

            userRepository.save(admin);
            logger.info("Usuario administrador creado exitosamente.");
        }
    }
}
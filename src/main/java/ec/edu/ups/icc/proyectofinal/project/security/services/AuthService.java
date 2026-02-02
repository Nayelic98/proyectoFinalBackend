package ec.edu.ups.icc.proyectofinal.project.security.services;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.ups.icc.proyectofinal.exceptions.domain.ConflictException;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.AuthResponseDto;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.GoogleLoginRequestDto;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.LoginRequestDto;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.RegisterRequestDto;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleEntity;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleName;
import ec.edu.ups.icc.proyectofinal.project.security.repository.RoleRepository;
import ec.edu.ups.icc.proyectofinal.project.security.utils.JwtUtil;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import ec.edu.ups.icc.proyectofinal.user.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository; // Inyectado
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(AuthenticationManager authenticationManager,
                        UserRepository userRepository,
                        RoleRepository roleRepository, // Inyectado
                        PasswordEncoder passwordEncoder,
                        JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    @Transactional(readOnly = true)
    public AuthResponseDto login(LoginRequestDto loginRequest) {
        // Se usa contacto en lugar de email para el login
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getContacto(),
                        loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        Set<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toSet());

        return new AuthResponseDto(
        jwt,
        userDetails.getId(),
        userDetails.getNombre(),
        userDetails.getUsername(),
        roles, // El Set va aquí
        userDetails.getMustChangePassword());
    }

    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerRequest) {
        if (userRepository.existsByContacto(registerRequest.getContacto())) {
            throw new ConflictException("El contacto ya está registrado");
        }

        UserEntity user = new UserEntity();
        user.setNombre(registerRequest.getNombre());
        user.setContacto(registerRequest.getContacto());
        user.setDescripcion(registerRequest.getDescripcion());
        user.setFoto(registerRequest.getFoto());
// Dentro del método register
user.setRedes(java.util.List.of(registerRequest.getRedes()));
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        // --- NUEVA LÓGICA DE ROLES ---
        String roleRequest = registerRequest.getRole(); // Ej: "programmer" o "user"
        RoleEntity roleEntity;

        if (roleRequest != null && roleRequest.equalsIgnoreCase("programmer")) {
            roleEntity = roleRepository.findByName(RoleName.ROLE_PROGRAMMER)
                .orElseThrow(() -> new RuntimeException("Error: Rol PROGRAMMER no encontrado."));
        } else {
            roleEntity = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));
        }

        user.getRoles().add(roleEntity); // Añadimos al Set<RoleEntity>
        // -----------------------------

        user = userRepository.save(user);

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);
        String jwt = jwtUtil.generateTokenFromUserDetails(userDetails);
        
        Set<String> roleNames = userDetails.getAuthorities().stream()
                .map(auth -> auth.getAuthority())
                .collect(Collectors.toSet());

        return new AuthResponseDto(
        jwt,
        user.getId(),
        user.getNombre(),
        user.getContacto(),
        roleNames,
        false);// Por defecto false en registro normal
        }
    @Transactional
public AuthResponseDto googleLogin(GoogleLoginRequestDto googleLoginRequest) {
    UserEntity user = userRepository.findByContacto(googleLoginRequest.getContacto())
            .orElseGet(() -> {
                UserEntity newUser = new UserEntity();
                newUser.setNombre(googleLoginRequest.getNombre());
                newUser.setContacto(googleLoginRequest.getContacto());
                newUser.setPassword(passwordEncoder.encode("GOOGLE_PRIVATE_AUTH"));
                newUser.setDescripcion("Usuario registrado vía Google");
                
                // Foto de Google o default
                String urlFoto = googleLoginRequest.getFoto();
                newUser.setFoto((urlFoto != null && !urlFoto.isEmpty()) ? urlFoto : "default-avatar.png");
                
                newUser.setRedes(new java.util.ArrayList<>());                newUser.setEspecialidad("General"); 
                newUser.setMustChangePassword(false);

                RoleEntity defaultRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));
                newUser.getRoles().add(defaultRole);
                
                return userRepository.save(newUser);
            });

    UserDetailsImpl userDetails = UserDetailsImpl.build(user);
    String jwt = jwtUtil.generateTokenFromUserDetails(userDetails);

    Set<String> roles = userDetails.getAuthorities().stream()
        .map(auth -> auth.getAuthority()
                         .replace("ROLE_", "") // Quita el prefijo
                         .toLowerCase())       // Lo deja en minúsculas (admin, user, etc.)
        .collect(Collectors.toSet());

    return new AuthResponseDto(
        jwt, 
        user.getId(), 
        user.getNombre(), 
        user.getContacto(), 
        roles,
        false // Usuarios de Google no suelen requerir cambio de clave
);
}
@Transactional
    public void updatePassword(String contacto, String newPassword) {
        System.out.println("Procesando actualización para: " + contacto);
        
        if (contacto == null || contacto.trim().isEmpty()) {
            throw new RuntimeException("Datos de actualización incompletos: contacto es nulo");
        }

        UserEntity user = userRepository.findByContacto(contacto)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con contacto: " + contacto));
        
        // Encriptamos la nueva contraseña antes de guardar
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setMustChangePassword(false); 
        
        userRepository.save(user);
        System.out.println("Contraseña actualizada con éxito en la BD.");
    }

}
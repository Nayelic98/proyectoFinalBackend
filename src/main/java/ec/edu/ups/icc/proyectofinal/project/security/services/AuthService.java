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
                userDetails.getUsername(), // Retorna el contacto
                roles);
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
        user.setRedes(registerRequest.getRedes());
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
                roleNames);
    }
    @Transactional
public AuthResponseDto googleLogin(GoogleLoginRequestDto googleLoginRequest) {
    // 1. Buscar si el usuario ya existe en Postgres por su contacto (email de Google)
    UserEntity user = userRepository.findByContacto(googleLoginRequest.getContacto())
            .orElseGet(() -> {
                // 2. Si NO existe, creamos uno nuevo (Registro automático)
                UserEntity newUser = new UserEntity();
                newUser.setNombre(googleLoginRequest.getNombre());
                newUser.setContacto(googleLoginRequest.getContacto());
                // Contraseña dummy porque es requerida en DB, pero entrará por Google
                newUser.setPassword(passwordEncoder.encode("GOOGLE_PRIVATE_AUTH"));
                newUser.setDescripcion("Usuario registrado vía Google");
                // Asignar rol de USER por defecto
                RoleEntity defaultRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));
                newUser.getRoles().add(defaultRole);
                
                return userRepository.save(newUser);
            });

    // 3. Crear el UserDetails a partir del usuario (existente o nuevo)
    UserDetailsImpl userDetails = UserDetailsImpl.build(user);

    // 4. Generar el Token JWT usando tu JwtUtil
    // Usamos el método que ya tienes: generateTokenFromUserDetails
    String jwt = jwtUtil.generateTokenFromUserDetails(userDetails);

    // 5. Obtener los nombres de los roles para la respuesta
    Set<String> roles = userDetails.getAuthorities().stream()
            .map(auth -> auth.getAuthority())
            .collect(Collectors.toSet());

    // 6. Retornar el DTO que Angular está esperando
    return new AuthResponseDto(
            jwt,
            user.getId(),
            user.getNombre(),
            user.getContacto(),
            roles);
}
}
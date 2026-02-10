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
        roles, 
        userDetails.getMustChangePassword());
    }
    @Transactional
    public AuthResponseDto register(RegisterRequestDto registerRequest) {
    if (userRepository.existsByContacto(registerRequest.getContacto())) {
        throw new ConflictException("El contacto ya está registrado");
    }
    UserEntity user = new UserEntity();
    String nombreDefault = (registerRequest.getNombre() != null) ? registerRequest.getNombre() : registerRequest.getContacto().split("@")[0];
    user.setNombre(nombreDefault);
    
    user.setContacto(registerRequest.getContacto());

    user.setDescripcion("Usuario recién registrado");
    user.setFoto("default-avatar.png");
    user.setEspecialidad("General");
    user.setRedes(new java.util.ArrayList<>());
    
    user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
    user.setMustChangePassword(false); 

    RoleEntity roleEntity = roleRepository.findByName(RoleName.ROLE_USER)
            .orElseThrow(() -> new RuntimeException("Error: Rol USER no encontrado."));
    user.getRoles().add(roleEntity);

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
        false
    );
}
   @Transactional
    public AuthResponseDto googleLogin(GoogleLoginRequestDto googleLoginRequest) {
    UserEntity user = userRepository.findByContacto(googleLoginRequest.getContacto())
            .orElseGet(() -> {
                UserEntity newUser = new UserEntity();
                
                newUser.setNombre(googleLoginRequest.getNombre());
                newUser.setContacto(googleLoginRequest.getContacto());

                newUser.setPassword(passwordEncoder.encode("GOOGLE_AUTH_" + java.util.UUID.randomUUID()));

                newUser.setDescripcion("Usuario registrado automáticamente vía Google");
                
                String urlFoto = googleLoginRequest.getFoto();
                newUser.setFoto((urlFoto != null && !urlFoto.isEmpty()) ? urlFoto : "default-avatar.png");
                
                newUser.setEspecialidad("General");
                newUser.setRedes(new java.util.ArrayList<>()); 

                newUser.setMustChangePassword(true);

                RoleEntity defaultRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new RuntimeException("ERROR CRÍTICO: El rol ROLE_USER no existe en la DB."));
                newUser.getRoles().add(defaultRole);
                
                return userRepository.save(newUser);
            });

    UserDetailsImpl userDetails = UserDetailsImpl.build(user);
    String jwt = jwtUtil.generateTokenFromUserDetails(userDetails);

    java.util.Set<String> roles = userDetails.getAuthorities().stream()
            .map(auth -> auth.getAuthority()) 
            .collect(java.util.stream.Collectors.toSet());

    return new AuthResponseDto(
            jwt, 
            user.getId(), 
            user.getNombre(), 
            user.getContacto(), 
            roles,
            user.isMustChangePassword()
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
        
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setMustChangePassword(false); 
        
        userRepository.save(user);
        System.out.println("Contraseña actualizada con éxito en la BD.");
    }
}
package ec.edu.ups.icc.proyectofinal.user.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.ups.icc.proyectofinal.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleEntity;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleName;
import ec.edu.ups.icc.proyectofinal.project.security.repository.RoleRepository;
import ec.edu.ups.icc.proyectofinal.user.mappers.UserMapper;
import ec.edu.ups.icc.proyectofinal.user.dtos.CreateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.SolicitudPostulacionDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UserResponseDto;
import ec.edu.ups.icc.proyectofinal.user.models.SolicitudPostulacionEntity;
import ec.edu.ups.icc.proyectofinal.user.models.User;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import ec.edu.ups.icc.proyectofinal.user.repository.SolicitudPostulacionRepository;
import ec.edu.ups.icc.proyectofinal.user.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {
    private final SolicitudPostulacionRepository solicitudRepo;
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder passwordEncoder, SolicitudPostulacionRepository solicitudRepo) {
        this.userRepo = userRepo;
        this.solicitudRepo = solicitudRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }
@Override
@Transactional
public void postularComoProgramador(SolicitudPostulacionDto dto) {
    // 1. Log para saber que entramos al servicio
    System.out.println("Procesando postulación para: " + dto.email);

    // 2. IMPORTANTE: Validar si ya existe (Evita el error de duplicados en la DB)
    if (solicitudRepo.existsByEmail(dto.email)) {
        throw new RuntimeException("Ya existe una solicitud pendiente con este correo.");
    }

    SolicitudPostulacionEntity entity = new SolicitudPostulacionEntity();
    entity.setNombre(dto.nombre);
    entity.setEmail(dto.email);
    entity.setEspecialidad(dto.especialidad);
    entity.setPortafolio(dto.portafolio);
    entity.setDescripcion(dto.descripcion);
    
    // 3. Establecer estado inicial explícito
    entity.setEstado("PENDIENTE"); 

    // 4. Guardar y forzar el flush para ver errores de inmediato
    SolicitudPostulacionEntity savedEntity = solicitudRepo.save(entity);
    System.out.println("¡Solicitud guardada con éxito!");
}

@Override
public List<SolicitudPostulacionEntity> findAllSolicitudes() {
    return solicitudRepo.findAll();
}

@Override
@Transactional
public void actualizarEstadoSolicitud(Long id, String nuevoEstado) {
    SolicitudPostulacionEntity solicitud = solicitudRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

    String estadoLimpio = nuevoEstado.replace("\"", "").trim();
    solicitud.setEstado(estadoLimpio);

    if ("APROBADO".equals(estadoLimpio)) {
    Optional<UserEntity> userOpt = userRepo.findByContacto(solicitud.getEmail());
    
    UserEntity usuario;
    if (userOpt.isPresent()) {
        usuario = userOpt.get();
        solicitud.setTemporalPassword("USA_TU_CLAVE_ACTUAL"); 
    } else {
        usuario = new UserEntity();
        usuario.setContacto(solicitud.getEmail());
        usuario.setNombre(solicitud.getNombre());
        
        String passPlano = solicitud.getNombre().trim().split(" ")[0] + "1234";
        usuario.setPassword(passwordEncoder.encode(passPlano));
        usuario.setMustChangePassword(true);
        solicitud.setTemporalPassword(passPlano);
    }

    // BUSCAMOS EL ROL
    RoleEntity role = roleRepo.findByName(RoleName.ROLE_PROGRAMMER)
            .orElseThrow(() -> new RuntimeException("Rol ROLE_PROGRAMMER no encontrado"));
    
    // LIMPIEZA Y ASIGNACIÓN SEGURA
    if (usuario.getRoles() == null) {
        usuario.setRoles(new HashSet<>());
    }
    usuario.getRoles().clear(); // Quitamos ROLE_USER
    usuario.getRoles().add(role); // Añadimos ROLE_PROGRAMMER
    
    // GUARDADO EXPLÍCITO
    userRepo.saveAndFlush(usuario); 
    System.out.println("Rol PROGRAMMER asignado a: " + usuario.getContacto());
}
}
@Override
public SolicitudPostulacionEntity obtenerSolicitudPorEmail(String email) {
    try {
        // Usamos findByEmail que devuelve un Optional
        return solicitudRepo.findByEmail(email)
                .orElse(null); // Si no existe, devuelve null (esto evita el Error 500)
    } catch (Exception e) {
        // Log para ver en la consola de Java qué pasó realmente
        System.err.println("Error al buscar solicitud por email: " + e.getMessage());
        return null;
    }
}

    @Override
@Transactional
public UserResponseDto create(CreateUserDto dto) {
    if (userRepo.existsByContacto(dto.contacto)) {
        throw new RuntimeException("El contacto ya está registrado");
    }

    User domainUser = UserMapper.fromCreateDto(dto);
    UserEntity entity = domainUser.toEntity();

    // 1. Password y Cambio Obligatorio
    String tempPassword = (dto.password != null && !dto.password.isEmpty()) 
                            ? dto.password : "123456";
    entity.setPassword(passwordEncoder.encode(tempPassword));
    entity.setMustChangePassword(true);

    // 2. ASIGNACIÓN DE ROL SEGÚN EL DTO
    // Si el DTO ya trae ROLE_PROGRAMMER (porque viene del controller de admin), se lo ponemos.
    // Si no trae nada, le ponemos ROLE_USER.
    
    RoleName roleSelected; 
    if ("ROLE_PROGRAMMER".equals(dto.role)) {
        roleSelected = RoleName.ROLE_PROGRAMMER;
    } else {
        roleSelected = RoleName.ROLE_USER;
    }

    // 2. Ahora roleSelected es "effectively final" porque no se vuelve a cambiar
    RoleEntity roleToAssign = roleRepo.findByName(roleSelected)
            .orElseThrow(() -> new RuntimeException("Error: Rol " + roleSelected + " no existe"));
    Set<RoleEntity> roles = new HashSet<>();
    roles.add(roleToAssign);
    entity.setRoles(roles);

    UserEntity savedEntity = userRepo.save(entity);
    return UserMapper.toResponse(User.fromEntity(savedEntity));
}
    @Override
    public List<UserResponseDto> findProgrammers() {
        // Ahora el filter encontrará al usuario porque tiene el ROLE_PROGRAMMER en su Set
        return userRepo.findAll().stream()
                .filter(entity -> entity.getRoles() != null && entity.getRoles().stream()
                        .anyMatch(role -> role.getName() == RoleName.ROLE_PROGRAMMER))
                .map(entity -> UserMapper.toResponse(User.fromEntity(entity)))
                .toList();
    }

    @Override
    public UserResponseDto findById(Long id) {
        UserEntity entity = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));
        return UserMapper.toResponse(User.fromEntity(entity));
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepo.findAll().stream()
                .map(entity -> UserMapper.toResponse(User.fromEntity(entity)))
                .toList();
    }

    @Override
@Transactional
public UserResponseDto update(Long id, UpdateUserDto dto) {
    // 1. Buscamos la entidad existente (gestionada por JPA)
    UserEntity existing = userRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
    
    // 2. Actualizamos los campos manualmente (o mediante un método en la entidad)
    // Esto asegura que NO se pierdan los roles ni el password
    if (dto.nombre != null) existing.setNombre(dto.nombre);
    if (dto.especialidad != null) existing.setEspecialidad(dto.especialidad);
    if (dto.descripcion != null) existing.setDescripcion(dto.descripcion);
    if (dto.foto != null) existing.setFoto(dto.foto);
    
    // IMPORTANTE: Manejo de la lista de redes
    if (dto.redes != null) {
        // En JPA/Hibernate es mejor limpiar y añadir para no romper la relación
        existing.getRedes().clear();
        existing.getRedes().addAll(dto.redes);
    }

    // 3. Guardamos la entidad original modificada
    UserEntity saved = userRepo.save(existing);
    
    // 4. Retornamos el DTO de respuesta
    return UserMapper.toResponse(User.fromEntity(saved));
}
    @Override
@Transactional
public void delete(Long id) {
    // 1. Buscamos al usuario
    UserEntity user = userRepo.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    // 2. LIMPIEZA MANUAL DE SOLICITUDES
    // Como no tienes un repositorio de solicitudes inyectado aquí directamente, 
    // lo más rápido es usar una consulta personalizada en el UserRepository o
    // borrar la solicitud asociada si tienes acceso al repositorio.
    
    // Si la solicitud está ligada al email (contacto):
    solicitudRepo.eliminarPorContacto(user.getContacto());

    // 3. LIMPIEZA DE ROLES (ManyToMany)
    // Esto limpia la tabla 'user_roles' para este ID
    user.getRoles().clear();
    
    // 4. LIMPIEZA DE REDES (@ElementCollection)
    user.getRedes().clear();

    // 5. BORRADO FINAL
    // Gracias al CascadeType.ALL en tu Entity, esto borrará los 'projects'
    userRepo.delete(user);
}
    @Override
    public UserResponseDto findByContacto(String contacto) {
        UserEntity entity = userRepo.findByContacto(contacto)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con contacto: " + contacto));
        return UserMapper.toResponse(User.fromEntity(entity));
    }
}
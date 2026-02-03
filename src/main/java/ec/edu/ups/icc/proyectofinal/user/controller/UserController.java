package ec.edu.ups.icc.proyectofinal.user.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.proyectofinal.user.dtos.CreateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.SolicitudPostulacionDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UserResponseDto;
import ec.edu.ups.icc.proyectofinal.user.models.SolicitudPostulacionEntity;
import ec.edu.ups.icc.proyectofinal.user.services.UserService;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/users") 
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all") // Cambiado de "" a "/all" para evitar conflictos
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/create-programmer")
    @PreAuthorize("hasAuthority('ADMIN') or hasRole('ADMIN')") 
    public ResponseEntity<UserResponseDto> createProgrammer(@RequestBody CreateUserDto dto) {
        System.out.println(">>> LOG: ¡Acceso concedido al Controller!");
        System.out.println(">>> Datos recibidos: Nombre=" + dto.nombre + ", Email=" + dto.contacto);
        
        dto.role = "ROLE_PROGRAMMER"; 
        return ResponseEntity.ok(userService.create(dto));
    }
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyProfile(Principal principal) {
        return ResponseEntity.ok(userService.findByContacto(principal.getName()));
    }
    // UserController.java


// Por esto (especificando el nombre "id"):
@PutMapping("/{id}")
public ResponseEntity<UserResponseDto> update(@PathVariable("id") Long id, @RequestBody UpdateUserDto dto) {
    UserResponseDto response = userService.update(id, dto);
    return ResponseEntity.ok(response);
}
    // Endpoint para que el programador logueado actualice su propio perfil
    @PutMapping("/profile/update")
    @PreAuthorize("hasRole('PROGRAMMER')")
    public ResponseEntity<UserResponseDto> updateSelf(@RequestBody UpdateUserDto dto, Principal principal) {
        // Obtenemos el usuario actual por su contacto (email) extraído del JWT
        UserResponseDto currentUser = userService.findByContacto(principal.getName());
        // Actualizamos usando su ID real
        return ResponseEntity.ok(userService.update(currentUser.id, dto));
    }
   @PostMapping("/postular")
public ResponseEntity<?> postular(@Valid @RequestBody SolicitudPostulacionDto dto) {
    userService.postularComoProgramador(dto);
    return ResponseEntity.ok().build();
}

// Endpoint para que el ADMIN vea las solicitudes en su panel
// Listar todas las solicitudes para el Admin
@GetMapping("/solicitudes-postulacion")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<List<SolicitudPostulacionEntity>> getSolicitudes() {
    return ResponseEntity.ok(userService.findAllSolicitudes());
}


// 1. Permite que tanto USER como PROGRAMMER puedan ver su solicitud
@GetMapping("/mi-solicitud")
@PreAuthorize("hasAnyRole('USER', 'PROGRAMMER')") // Cambiado para que no falle al ser aprobado
public ResponseEntity<SolicitudPostulacionEntity> getMiSolicitud(Principal principal) {
    System.out.println(">>> [DEBUG] Buscando solicitud para: " + principal.getName());
    SolicitudPostulacionEntity solicitud = userService.obtenerSolicitudPorEmail(principal.getName());
    
    if (solicitud == null) {
        return ResponseEntity.noContent().build();
    }
    return ResponseEntity.ok(solicitud);
}

// 2. Agregamos prints de debug al cambio de estado para que LOS VEAS EN LA CONSOLA DEL IDE
@PatchMapping("/solicitudes-postulacion/{id}/estado")
@PreAuthorize("hasRole('ADMIN')")
public ResponseEntity<Void> cambiarEstadoSolicitud(
        @PathVariable("id") Long id, 
        @RequestBody String nuevoEstado) {
    
    // ESTOS PRINTS APARECERÁN EN TU IDE (IntelliJ/Eclipse)
    System.out.println(">>> [ADMIN] Petición de cambio de estado recibida");
    System.out.println(">>> [ADMIN] ID de solicitud: " + id);
    System.out.println(">>> [ADMIN] Valor recibido: " + nuevoEstado);

    userService.actualizarEstadoSolicitud(id, nuevoEstado);
    
    System.out.println(">>> [ADMIN] ¡Proceso completado con éxito en el Service!");
    return ResponseEntity.noContent().build();
}

    @GetMapping("/programadores")
    public ResponseEntity<List<UserResponseDto>> getProgrammers() {
        return ResponseEntity.ok(userService.findProgrammers());
    }

    // Corregido con ("id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // Corregido con ("id")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
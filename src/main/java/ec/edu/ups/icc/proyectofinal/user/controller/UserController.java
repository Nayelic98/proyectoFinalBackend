package ec.edu.ups.icc.proyectofinal.user.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.proyectofinal.user.dtos.UserResponseDto;
import ec.edu.ups.icc.proyectofinal.user.services.UserService;

@RestController
@RequestMapping("/api/users") // Ruta base
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')") // <--- Solo tu nuevo token de Admin pasará por aquí
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.findAll();
        return ResponseEntity.ok(users);
    }
}
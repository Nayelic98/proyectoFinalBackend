package ec.edu.ups.icc.proyectofinal.project.security.controllers;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ec.edu.ups.icc.proyectofinal.project.security.dtos.AuthResponseDto;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.GoogleLoginRequestDto;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.LoginRequestDto;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.RegisterRequestDto;
import ec.edu.ups.icc.proyectofinal.project.security.dtos.UpdatePasswordRequestDto;
import ec.edu.ups.icc.proyectofinal.project.security.services.AuthService;
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/google-login")
    public ResponseEntity<AuthResponseDto> googleLogin(@Valid @RequestBody GoogleLoginRequestDto googleLoginRequest) {

        AuthResponseDto response = authService.googleLogin(googleLoginRequest);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {

        AuthResponseDto response = authService.login(loginRequest);
        return ResponseEntity.ok(response);

    }
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto registerRequest) {

        AuthResponseDto response = authService.register(registerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @PutMapping("/update-password")
public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequestDto request) {
    System.out.println("JSON recibido - Contacto: " + request.getContacto());
    System.out.println("JSON recibido - Password: " + request.getNewPassword());
    authService.updatePassword(request.getContacto(), request.getNewPassword());
    return ResponseEntity.ok("OK");
}
}

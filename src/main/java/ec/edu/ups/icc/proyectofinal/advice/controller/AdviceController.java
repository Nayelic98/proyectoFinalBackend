package ec.edu.ups.icc.proyectofinal.advice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.proyectofinal.advice.dtos.AdviceResponseDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.CreateAdviceDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.UpdateAdviceDto;
import ec.edu.ups.icc.proyectofinal.advice.services.AdviceService;

@RestController
@RequestMapping("/api/asesorias")
@CrossOrigin(origins = "*") 
public class AdviceController {
    private final AdviceService adviceService;

    public AdviceController(AdviceService adviceService) {
        this.adviceService = adviceService;
    }

@GetMapping("/programador/{id}")
public ResponseEntity<List<AdviceResponseDto>> getByProgramador(@PathVariable("id") Long id) { 
    return ResponseEntity.ok(adviceService.findByProgramadorId(id));
}
@PostMapping
public ResponseEntity<AdviceResponseDto> create(@RequestBody CreateAdviceDto dto) {
    return ResponseEntity.ok(adviceService.create(dto));
}
@PutMapping("/{id}")
public ResponseEntity<AdviceResponseDto> update(@PathVariable("id") Long id, @RequestBody UpdateAdviceDto dto) {
    return ResponseEntity.ok(adviceService.update(id, dto));
}
    @GetMapping("/usuario/{id}")
public ResponseEntity<List<AdviceResponseDto>> getByUsuario(@PathVariable("id") Long id) {
    return ResponseEntity.ok(adviceService.findByUsuarioId(id));
}
}
package ec.edu.ups.icc.proyectofinal.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ec.edu.ups.icc.proyectofinal.project.dtos.CreateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.ProjectResponseDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.UpdateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.security.services.UserDetailsImpl;
import ec.edu.ups.icc.proyectofinal.project.services.ProjectService;
@RestController
@RequestMapping("/api/proyectos")
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    @GetMapping
    public ResponseEntity<List<ProjectResponseDto>> getAll() {
        return ResponseEntity.ok(projectService.findAll(0, 1000, new String[]{"id", "asc"}).getContent());
    }
    @GetMapping("/programador/{uid}")
    public ResponseEntity<List<ProjectResponseDto>> getByProgramador(@PathVariable("uid") Long uid) {
        return ResponseEntity.ok(projectService.findByProgramadorId(uid));
    }
    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'PROGRAMMER')")
    public ResponseEntity<ProjectResponseDto> create(@RequestBody CreateProjectDto dto) {
        return ResponseEntity.ok(projectService.create(dto));
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROGRAMMER')")
    public ResponseEntity<ProjectResponseDto> update(
            @PathVariable("id") Long id, 
            @RequestBody UpdateProjectDto dto,
            @AuthenticationPrincipal UserDetailsImpl currentUser) { 
        return ResponseEntity.ok(projectService.update(id, dto, currentUser));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PROGRAMMER')")
    public ResponseEntity<Void> delete(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal UserDetailsImpl currentUser) { 
        projectService.delete(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
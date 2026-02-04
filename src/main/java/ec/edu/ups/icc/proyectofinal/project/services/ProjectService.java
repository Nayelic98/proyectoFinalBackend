package ec.edu.ups.icc.proyectofinal.project.services;

import java.util.List;

import org.springframework.data.domain.Page;

import ec.edu.ups.icc.proyectofinal.project.dtos.CreateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.ProjectResponseDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.UpdateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.security.services.UserDetailsImpl;

public interface ProjectService {

    ProjectResponseDto create(CreateProjectDto dto);

    Page<ProjectResponseDto> findAll(int page, int size, String[] sort);

    ProjectResponseDto findById(Long id);

    List<ProjectResponseDto> findByProgramadorId(Long programadorId);

    ProjectResponseDto update(Long id, UpdateProjectDto dto, UserDetailsImpl currentUser);

    void delete(Long id, UserDetailsImpl currentUser);
}

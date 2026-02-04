package ec.edu.ups.icc.proyectofinal.project.mappers;

import ec.edu.ups.icc.proyectofinal.project.dtos.CreateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.ProjectResponseDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.UpdateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.models.Project;

public class ProjectMapper {

    /**
     * DTO de Creación -> Modelo de Dominio
     */
    public static Project fromCreateDto(CreateProjectDto dto) {
        Project project = new Project();
        project.setNombre(dto.nombre);
        project.setDescripcion(dto.descripcion);
        project.setCategoria(dto.categoria);
        project.setTipo(dto.tipo);
        project.setDeploy(dto.deploy);
        project.setRepo(dto.repo);
        project.setTecnologias(dto.tecnologias);
        project.setAssignedToId(dto.assignedToId);
        return project;
    }

    /**
     * DTO de Actualización -> Modelo de Dominio
     */
    public static Project fromUpdateDto(UpdateProjectDto dto) {
        Project project = new Project();
        project.setNombre(dto.nombre);
        project.setDescripcion(dto.descripcion);
        project.setCategoria(dto.categoria);
        project.setTipo(dto.tipo);
        project.setDeploy(dto.deploy);
        project.setRepo(dto.repo);
        project.setTecnologias(dto.tecnologias);
        return project;
    }

    /**
     * Modelo de Dominio -> DTO de Respuesta
     * Nota: El mapeo del objeto anidado 'assignedTo' se suele completar en el Service
     * usando el repositorio de usuarios.
     */
    public static ProjectResponseDto toResponse(Project project) {
        ProjectResponseDto dto = new ProjectResponseDto();
        dto.id = project.getId();
        dto.nombre = project.getNombre();
        dto.descripcion = project.getDescripcion();
        dto.categoria = project.getCategoria();
        dto.tipo = project.getTipo();
        dto.deploy = project.getDeploy();
        dto.repo = project.getRepo();
        dto.tecnologias = project.getTecnologias();
        
        // El objeto assignedTo (UserSummaryDto) se inicializa en el Service
        return dto;
    }
}
package ec.edu.ups.icc.proyectofinal.project.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ec.edu.ups.icc.proyectofinal.exceptions.domain.BadRequestException;
import ec.edu.ups.icc.proyectofinal.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.proyectofinal.project.dtos.CreateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.ProjectResponseDto;
import ec.edu.ups.icc.proyectofinal.project.dtos.UpdateProjectDto;
import ec.edu.ups.icc.proyectofinal.project.mappers.ProjectMapper;
import ec.edu.ups.icc.proyectofinal.project.models.Project;
import ec.edu.ups.icc.proyectofinal.project.models.ProjectEntity;
import ec.edu.ups.icc.proyectofinal.project.repository.ProjectRepository;
import ec.edu.ups.icc.proyectofinal.project.security.models.RoleName;
import ec.edu.ups.icc.proyectofinal.project.security.services.UserDetailsImpl;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import ec.edu.ups.icc.proyectofinal.user.repository.UserRepository;

@Service
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepo;
    private final UserRepository userRepo;

    public ProjectServiceImpl(ProjectRepository projectRepo, UserRepository userRepo) {
        this.projectRepo = projectRepo;
        this.userRepo = userRepo;
    }
   @Override
    @Transactional
    public ProjectResponseDto create(CreateProjectDto dto) {
    UserEntity programador = userRepo.findById(dto.assignedToId)
            .orElseThrow(() -> new NotFoundException("El programador con ID " + dto.assignedToId + " no existe"));

    boolean esProgramador = programador.getRoles().stream()
            .anyMatch(role -> role.getName().equals(RoleName.ROLE_PROGRAMMER));

    if (!esProgramador) {
        throw new BadRequestException("Solo se pueden asignar proyectos a usuarios con el rol 'PROGRAMMER'");
    }
        Project project = ProjectMapper.fromCreateDto(dto);
        ProjectEntity entity = project.toEntity();
        entity.setAssignedTo(programador);

        ProjectEntity saved = projectRepo.save(entity);
        return toResponseDto(saved);
    }
    @Override
    public Page<ProjectResponseDto> findAll(int page, int size, String[] sort) {
        Pageable pageable = createPageable(page, size, sort);
        return projectRepo.findAll(pageable).map(this::toResponseDto);
    }
    @Override
    public ProjectResponseDto findById(Long id) {
        ProjectEntity entity = projectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Proyecto no encontrado con ID: " + id));
        return toResponseDto(entity);
    }
    @Override
    public List<ProjectResponseDto> findByProgramadorId(Long programadorId) {
        if (!userRepo.existsById(programadorId)) {
            throw new NotFoundException("Usuario no encontrado");
        }
        return projectRepo.findByAssignedToId(programadorId).stream()
                .map(this::toResponseDto)
                .toList();
    }
    @Override
    @Transactional
    public ProjectResponseDto update(Long id, UpdateProjectDto dto, UserDetailsImpl currentUser) {
    ProjectEntity existingEntity = projectRepo.findById(id)
            .orElseThrow(() -> new NotFoundException("Proyecto no encontrado"));
    validateOwnership(existingEntity, currentUser);
    if (dto.getNombre() != null && !dto.getNombre().isBlank()) {
        existingEntity.setNombre(dto.getNombre());
    }
    if (dto.getDescripcion() != null) {
        existingEntity.setDescripcion(dto.getDescripcion());
    }
    if (dto.getCategoria() != null) {
        existingEntity.setCategoria(dto.getCategoria());
    }
    if (dto.getTipo() != null) {
        existingEntity.setTipo(dto.getTipo());
    }
    if (dto.getRepo() != null) {
        existingEntity.setRepo(dto.getRepo());
    }
    if (dto.getDeploy() != null) {
        existingEntity.setDeploy(dto.getDeploy());
    }
    if (dto.getTecnologias() != null) {
        existingEntity.getTecnologias().clear();
        existingEntity.getTecnologias().addAll(dto.getTecnologias());
    }
    ProjectEntity saved = projectRepo.save(existingEntity);
    return toResponseDto(saved);
}
    @Override
    @Transactional
    public void delete(Long id, UserDetailsImpl currentUser) {
        ProjectEntity entity = projectRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Proyecto no encontrado"));
        validateOwnership(entity, currentUser);
        projectRepo.delete(entity);
    }
    private ProjectResponseDto toResponseDto(ProjectEntity entity) {
        ProjectResponseDto dto = ProjectMapper.toResponse(Project.fromEntity(entity));
        ProjectResponseDto.UserSummaryDto summary = new ProjectResponseDto.UserSummaryDto();
        summary.id = entity.getAssignedTo().getId();
        summary.nombre = entity.getAssignedTo().getNombre();
        summary.contacto = entity.getAssignedTo().getContacto();
        summary.especialidad = entity.getAssignedTo().getEspecialidad();
        
        dto.assignedTo = summary;
        return dto;
    }
    private void validateOwnership(ProjectEntity project, UserDetailsImpl currentUser) {
        boolean isAdmin = currentUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !project.getAssignedTo().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("No tienes permiso para modificar este proyecto");
        }
    }
    private Pageable createPageable(int page, int size, String[] sort) {
        Sort sortObj = (sort != null && sort.length >= 2) 
            ? Sort.by(Sort.Direction.fromString(sort[1]), sort[0]) 
            : Sort.by("id").descending();
            
        return PageRequest.of(page, size, sortObj);
    }
}

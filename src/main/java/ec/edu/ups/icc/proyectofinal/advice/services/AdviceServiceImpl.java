package ec.edu.ups.icc.proyectofinal.advice.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import ec.edu.ups.icc.proyectofinal.advice.dtos.AdviceResponseDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.CreateAdviceDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.UpdateAdviceDto;
import ec.edu.ups.icc.proyectofinal.advice.models.AdviceEntity;
import ec.edu.ups.icc.proyectofinal.advice.repository.AdviceRepository;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import ec.edu.ups.icc.proyectofinal.user.repository.UserRepository;
import jakarta.transaction.Transactional;
@Service
public class AdviceServiceImpl implements AdviceService {

    private final AdviceRepository adviceRepository;
    private final UserRepository userRepository; // Inyectar UserRepository

    public AdviceServiceImpl(AdviceRepository adviceRepository, UserRepository userRepository) {
        this.adviceRepository = adviceRepository;
        this.userRepository = userRepository;
    }
@Override
@Transactional
public AdviceResponseDto update(Long id, UpdateAdviceDto dto) {

    AdviceEntity advice = adviceRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));

    advice.setEstado(dto.estado);
    advice.setMensajeRespuesta(dto.mensajeRespuesta);
    

    if (dto.fecha != null && !dto.fecha.isEmpty()) {
        advice.setFecha(dto.fecha); 
    }
    AdviceEntity updated = adviceRepository.save(advice);
    return mapToResponseDto(updated); 
}
private AdviceResponseDto mapToResponseDto(AdviceEntity entity) {
    AdviceResponseDto dto = new AdviceResponseDto();

    dto.setId(entity.getId());
    dto.setNombreUsuario(entity.getNombreUsuario());
    dto.setTelefono(entity.getTelefono());
    dto.setMensaje(entity.getMensaje());
    dto.setEstado(entity.getEstado());
    dto.setMensajeRespuesta(entity.getMensajeRespuesta());
    dto.setFecha(entity.getFecha());
    dto.setCreatedAt(entity.getCreatedAt());

    if (entity.getUsuario() != null) {
        AdviceResponseDto.UserSummaryDto userSummary = new AdviceResponseDto.UserSummaryDto();
        userSummary.id = entity.getUsuario().getId();
        userSummary.nombre = entity.getUsuario().getNombre();
        userSummary.contacto = entity.getUsuario().getContacto();
        dto.setUsuario(userSummary);
    }

    if (entity.getProgramador() != null) {
        AdviceResponseDto.UserSummaryDto progSummary = new AdviceResponseDto.UserSummaryDto();
        progSummary.id = entity.getProgramador().getId();
        progSummary.nombre = entity.getProgramador().getNombre();
        progSummary.contacto = entity.getProgramador().getContacto();
        dto.setProgramador(progSummary);
    }

    return dto;
}
   @Override
@Transactional
public AdviceResponseDto create(CreateAdviceDto dto) {

    System.out.println("DTO RECIBIDO: " + dto.getNombreUsuario() + " | MSG: " + dto.getMensaje() + " | ID: " + dto.getUsuarioId());

    if (dto.getUsuarioId() == null || dto.getProgramadorId() == null) {
        throw new RuntimeException("Error: IDs nulos en el servidor");
    }

    UserEntity usuario = userRepository.findById(dto.getUsuarioId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    UserEntity programador = userRepository.findById(dto.getProgramadorId())
        .orElseThrow(() -> new RuntimeException("Programador no encontrado"));

    AdviceEntity entity = new AdviceEntity();
    
entity.setNombreUsuario(usuario.getNombre());
    entity.setMensaje(dto.getMensaje() != null ? dto.getMensaje() : "Sin mensaje");
    
    entity.setTelefono(dto.telefono);
    entity.setEstado("pendiente");
    entity.setMensajeRespuesta("");
    
    if (dto.fecha != null) {
        entity.setFecha(dto.fecha);
    }

    entity.setUsuario(usuario);
    entity.setProgramador(programador);

    return mapToResponseDto(adviceRepository.save(entity));
}
@Override
@Transactional
public List<AdviceResponseDto> findByUsuarioId(Long usuarioId) {

    List<AdviceEntity> entidades = adviceRepository.findByUsuarioId(usuarioId);
    return entidades.stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
}
   @Override
@Transactional 
public List<AdviceResponseDto> findByProgramadorId(Long programadorId) {
    List<AdviceEntity> entidades = adviceRepository.findByProgramadorId(programadorId);
    return entidades.stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
}
@Override
@Transactional
public List<AdviceResponseDto> findAll() {
    // Obtenemos todas las entidades desde el repositorio
    List<AdviceEntity> entidades = adviceRepository.findAll();
    
    // Transformamos la lista de entidades a la lista de DTOs de respuesta
    return entidades.stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
}
}
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
    // 1. Buscar la asesoría
    AdviceEntity advice = adviceRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Asesoría no encontrada"));

    // 2. Actualizar campos
    advice.setEstado(dto.estado); // "aceptada" o "negada"
    advice.setMensajeRespuesta(dto.mensajeRespuesta);
    
    // Si el DTO trae una nueva fecha (opcional)
    if (dto.fecha != null && !dto.fecha.isEmpty()) {
        advice.setFecha(dto.fecha); // Asegúrate de que el formato sea correcto
    }

    // 3. Guardar y retornar
    AdviceEntity updated = adviceRepository.save(advice);
    return mapToResponseDto(updated); // Tu método que convierte Entity a ResponseDto
}

private AdviceResponseDto mapToResponseDto(AdviceEntity entity) {
    AdviceResponseDto dto = new AdviceResponseDto();
    
    // 1. Campos simples (usando setters o acceso directo, ya que son públicos)
    dto.setId(entity.getId());
    dto.setNombreUsuario(entity.getNombreUsuario());
    dto.setTelefono(entity.getTelefono());
    dto.setMensaje(entity.getMensaje());
    dto.setEstado(entity.getEstado());
    dto.setMensajeRespuesta(entity.getMensajeRespuesta());
    dto.setFecha(entity.getFecha());
    dto.setCreatedAt(entity.getCreatedAt());

    // 2. Mapeo del objeto Usuario (UserSummaryDto)
    if (entity.getUsuario() != null) {
        AdviceResponseDto.UserSummaryDto userSummary = new AdviceResponseDto.UserSummaryDto();
        userSummary.id = entity.getUsuario().getId();
        userSummary.nombre = entity.getUsuario().getNombre();
        userSummary.contacto = entity.getUsuario().getContacto();
        dto.setUsuario(userSummary); // Aquí usamos el setter del objeto completo
    }

    // 3. Mapeo del objeto Programador (UserSummaryDto)
    if (entity.getProgramador() != null) {
        AdviceResponseDto.UserSummaryDto progSummary = new AdviceResponseDto.UserSummaryDto();
        progSummary.id = entity.getProgramador().getId();
        progSummary.nombre = entity.getProgramador().getNombre();
        progSummary.contacto = entity.getProgramador().getContacto();
        dto.setProgramador(progSummary); // Aquí usamos el setter del objeto completo
    }

    return dto;
}
   @Override
@Transactional
public AdviceResponseDto create(CreateAdviceDto dto) {
    // LOG DE CONTROL: Esto aparecerá en tu consola de Java
    System.out.println("DTO RECIBIDO: " + dto.getNombreUsuario() + " | MSG: " + dto.getMensaje() + " | ID: " + dto.getUsuarioId());

    if (dto.getUsuarioId() == null || dto.getProgramadorId() == null) {
        throw new RuntimeException("Error: IDs nulos en el servidor");
    }

    UserEntity usuario = userRepository.findById(dto.getUsuarioId())
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    UserEntity programador = userRepository.findById(dto.getProgramadorId())
        .orElseThrow(() -> new RuntimeException("Programador no encontrado"));

    AdviceEntity entity = new AdviceEntity();
    
    // Forzamos valores si vienen nulos para que no rompa la DB
entity.setNombreUsuario(usuario.getNombre());
    entity.setMensaje(dto.getMensaje() != null ? dto.getMensaje() : "Sin mensaje");
    
    entity.setTelefono(dto.telefono);
    entity.setEstado("pendiente");
    entity.setMensajeRespuesta("");
    
    // Parsear fecha si es String
    if (dto.fecha != null) {
        entity.setFecha(dto.fecha);
    }

    entity.setUsuario(usuario);
    entity.setProgramador(programador);

    return mapToResponseDto(adviceRepository.save(entity));
}
    
   // En AdviceServiceImpl.java añade:
@Override
@Transactional
public List<AdviceResponseDto> findByUsuarioId(Long usuarioId) {
    // Necesitas tener este método definido en tu AdviceRepository
    List<AdviceEntity> entidades = adviceRepository.findByUsuarioId(usuarioId);
    return entidades.stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
}
   @Override
@Transactional // Asegúrate de importar org.springframework.transaction.annotation.Transactional
public List<AdviceResponseDto> findByProgramadorId(Long programadorId) {
    List<AdviceEntity> entidades = adviceRepository.findByProgramadorId(programadorId);
    return entidades.stream()
            .map(this::mapToResponseDto)
            .collect(Collectors.toList());
}
}
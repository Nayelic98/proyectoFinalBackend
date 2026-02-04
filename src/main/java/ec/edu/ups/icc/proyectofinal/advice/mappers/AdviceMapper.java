package ec.edu.ups.icc.proyectofinal.advice.mappers;

import ec.edu.ups.icc.proyectofinal.advice.dtos.AdviceResponseDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.CreateAdviceDto;
import ec.edu.ups.icc.proyectofinal.advice.dtos.UpdateAdviceDto;
import ec.edu.ups.icc.proyectofinal.advice.models.Advice;

public class AdviceMapper {

    /**
     * DTO de Creación -> Modelo de Dominio
     */
    public static Advice fromCreateDto(CreateAdviceDto dto) {
        Advice advice = new Advice();
        advice.setNombreUsuario(dto.nombreUsuario);
        advice.setTelefono(dto.telefono);
        advice.setMensaje(dto.mensaje);
        advice.setUsuarioId(dto.usuarioId);
        advice.setProgramadorId(dto.programadorId);
        // El estado inicial se maneja usualmente en la lógica de negocio o entidad como "pendiente"
        return advice;
    }

    /**
     * DTO de Actualización -> Modelo de Dominio
     * Se usa cuando el programador responde a la solicitud.
     */
    public static Advice fromUpdateDto(UpdateAdviceDto dto) {
        Advice advice = new Advice();
        advice.setEstado(dto.estado);
        advice.setMensajeRespuesta(dto.mensajeRespuesta);
        advice.setFecha(dto.fecha);
        return advice;
    }

    public static AdviceResponseDto toResponse(Advice advice) {
        AdviceResponseDto dto = new AdviceResponseDto();
        dto.id = advice.getId();
        dto.nombreUsuario = advice.getNombreUsuario();
        dto.telefono = advice.getTelefono();
        dto.mensaje = advice.getMensaje();
        dto.estado = advice.getEstado();
        dto.mensajeRespuesta = advice.getMensajeRespuesta();
        dto.fecha = advice.getFecha();
        dto.createdAt = advice.getCreatedAt();
        
        // Nota: Los objetos 'usuario' y 'programador' (UserSummaryDto) 
        // se deben asignar en el Service tras recuperar las entidades correspondientes.
        return dto;
    }
}

package ec.edu.ups.icc.proyectofinal.user.services;

import java.util.List;

import ec.edu.ups.icc.proyectofinal.user.dtos.CreateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.SolicitudPostulacionDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UserResponseDto;
import ec.edu.ups.icc.proyectofinal.user.models.SolicitudPostulacionEntity;

public interface UserService {
    UserResponseDto create(CreateUserDto dto);
    UserResponseDto update(Long id, UpdateUserDto dto);
    UserResponseDto findById(Long id);
    List<UserResponseDto> findAll();
    void delete(Long id);
    UserResponseDto findByContacto(String contacto);
    List<UserResponseDto> findProgrammers(); 
    void postularComoProgramador(SolicitudPostulacionDto dto);
List<SolicitudPostulacionEntity> findAllSolicitudes(); 

void actualizarEstadoSolicitud(Long id, String nuevoEstado);// Para el panel de admin
SolicitudPostulacionEntity obtenerSolicitudPorEmail(String email);
}

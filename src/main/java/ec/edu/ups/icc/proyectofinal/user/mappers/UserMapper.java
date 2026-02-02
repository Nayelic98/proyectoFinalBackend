package ec.edu.ups.icc.proyectofinal.user.mappers;

import ec.edu.ups.icc.proyectofinal.user.dtos.CreateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UserResponseDto;
import ec.edu.ups.icc.proyectofinal.user.models.User;

public class UserMapper {

    public static User toModel(Long id, String nombre, String contacto, String role) {
        return new User(id, nombre, contacto, null, null, null, null, role, false);
    }

    public static User fromCreateDto(CreateUserDto dto) {
        return new User(
            null, 
            dto.nombre, 
            dto.contacto, 
            dto.descripcion, 
            dto.especialidad, 
            dto.foto, 
            dto.redes, 
            dto.role, 
            false
        );
    }   

    public static User fromUpdateDto(UpdateUserDto dto) {
        return new User(
            null, 
            dto.nombre, 
            dto.contacto, 
            dto.descripcion, 
            dto.especialidad, 
            dto.foto, 
            dto.redes, 
            null, // No permitimos cambiar rol desde un update común
            false
        );
    } 

    public static UserResponseDto toResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.nombre = user.getNombre();
        dto.contacto = user.getContacto();
        
        dto.especialidad = user.getEspecialidad();
        dto.descripcion = user.getDescripcion();
        dto.foto = user.getFoto();
dto.redes = user.getRedes();        dto.mustChangePassword = user.isMustChangePassword();
dto.role = (user.getRole() != null) ? user.getRole() : "ROLE_USER";        
        return dto;
    }
}
package ec.edu.ups.icc.proyectofinal.user.mappers;

import ec.edu.ups.icc.proyectofinal.user.dtos.CreateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UserResponseDto;
import ec.edu.ups.icc.proyectofinal.user.models.User;

public class UserMapper {
// Simulación de modelo base para búsquedas o transformaciones simples
    public static User toModel(Long id, String nombre, String contacto, String role) {
        return new User(id, nombre, contacto, null, null, null, null, role, false);
    }

    // DTO de Creación -> Model (id se pone en 0 o null porque Postgres lo genera)
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

    // DTO de Actualización -> Model
    public static User fromUpdateDto(UpdateUserDto dto) {
        return new User(
            null, 
            dto.nombre, 
            dto.contacto, 
            dto.descripcion, 
            dto.especialidad, 
            dto.foto, 
            dto.redes, 
            null, // El rol usualmente no se cambia en un Update común
            false
        );
    }   

    // Model -> DTO de Respuesta (Lo que ve el cliente)
    public static UserResponseDto toResponse(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.id = user.getId();
        dto.nombre = user.getNombre();
        dto.contacto = user.getContacto();
        dto.role = user.getRole();
        return dto;
    }
}

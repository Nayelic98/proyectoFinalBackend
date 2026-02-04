package ec.edu.ups.icc.proyectofinal.user.services;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.edu.ups.icc.proyectofinal.exceptions.domain.NotFoundException;
import ec.edu.ups.icc.proyectofinal.user.mappers.UserMapper;
import ec.edu.ups.icc.proyectofinal.user.dtos.CreateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UpdateUserDto;
import ec.edu.ups.icc.proyectofinal.user.dtos.UserResponseDto;
import ec.edu.ups.icc.proyectofinal.user.models.User;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import ec.edu.ups.icc.proyectofinal.user.repository.UserRepository;
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    public UserServiceImpl(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserResponseDto create(CreateUserDto dto) {
        if (userRepo.existsByContacto(dto.contacto)) {
            throw new RuntimeException("El contacto ya está registrado");
        }

        User user = UserMapper.fromCreateDto(dto);
        
       
        UserEntity savedEntity = userRepo.save(user.toEntity());
        
      
        return UserMapper.toResponse(User.fromEntity(savedEntity));
    }

    @Override
    public UserResponseDto findById(Long id) {
        UserEntity entity = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con ID: " + id));
        
        return UserMapper.toResponse(User.fromEntity(entity));
    }

    @Override
    public List<UserResponseDto> findAll() {
        return userRepo.findAll().stream()
                .map(entity -> UserMapper.toResponse(User.fromEntity(entity)))
                .toList();
    }

    @Override
    public UserResponseDto update(Long id, UpdateUserDto dto) {
        UserEntity existing = userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado"));
        
        // Mapeamos la entidad al modelo de dominio para aplicar la lógica de update
        User user = User.fromEntity(existing);
        user.update(dto);
        
        // Guardamos los cambios
        UserEntity saved = userRepo.save(user.toEntity());
        
        return UserMapper.toResponse(User.fromEntity(saved));
    }

    @Override
    public void delete(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException("Usuario no encontrado");
        }
        userRepo.deleteById(id);
    }

    @Override
    public UserResponseDto findByContacto(String contacto) {
        UserEntity entity = userRepo.findByContacto(contacto)
                .orElseThrow(() -> new NotFoundException("Usuario no encontrado con contacto: " + contacto));
        
        return UserMapper.toResponse(User.fromEntity(entity));
    }

  

    
}
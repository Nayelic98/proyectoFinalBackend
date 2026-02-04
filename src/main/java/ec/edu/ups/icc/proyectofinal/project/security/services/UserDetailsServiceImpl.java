package ec.edu.ups.icc.proyectofinal.project.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;
import ec.edu.ups.icc.proyectofinal.user.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
@Transactional
public UserDetails loadUserByUsername(String contacto) throws UsernameNotFoundException {
    UserEntity user = userRepository.findByContacto(contacto)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con contacto: " + contacto));

    return UserDetailsImpl.build(user);
}
}

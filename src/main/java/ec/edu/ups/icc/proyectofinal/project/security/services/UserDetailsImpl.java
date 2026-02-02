package ec.edu.ups.icc.proyectofinal.project.security.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import ec.edu.ups.icc.proyectofinal.user.models.UserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String contacto;
    private Boolean mustChangePassword;
    
    
    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String nombre, String contacto, String password, 
                          Collection<? extends GrantedAuthority> authorities,Boolean mustChangePassword) {
        this.id = id;
        this.nombre = nombre;
        this.contacto = contacto;
        this.password = password; // Ahora sí se asigna correctamente
        this.authorities = authorities;
        this.mustChangePassword = mustChangePassword;
        
    }

    public static UserDetailsImpl build(UserEntity user) {
        // --- NUEVA LÓGICA: Convertir el Set de RoleEntity a GrantedAuthorities ---
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new UserDetailsImpl(
            user.getId(),
            user.getNombre(),
            user.getContacto(),
            user.getPassword(), 
            authorities,         // 5to parámetro
            user.isMustChangePassword() // 6to parámetro
    );
    }
   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; 
    }

    public Long getId() { return id; }
    
    public String getNombre() { return nombre; }

    @Override
    public String getPassword() { return password; }

    @Override
    public String getUsername() { return contacto; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public Boolean getMustChangePassword() {
        return mustChangePassword;
    }

    public void setMustChangePassword(Boolean mustChangePassword) {
        this.mustChangePassword = mustChangePassword;
    }
}
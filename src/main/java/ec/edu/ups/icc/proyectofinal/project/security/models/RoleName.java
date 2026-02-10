package ec.edu.ups.icc.proyectofinal.project.security.models;

public enum RoleName {
    ROLE_USER("Usuario estándar con permisos básicos"),
    ROLE_ADMIN("Administrador con permisos completos"),
    ROLE_PROGRAMMER("Programador con permisos para gestionar proyectos");
    private final String description;

    RoleName(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}

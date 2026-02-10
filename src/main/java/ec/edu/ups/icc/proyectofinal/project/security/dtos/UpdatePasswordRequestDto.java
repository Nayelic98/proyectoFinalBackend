package ec.edu.ups.icc.proyectofinal.project.security.dtos;
public class UpdatePasswordRequestDto {
    private String contacto;
    private String newPassword;
public UpdatePasswordRequestDto() {}

    public String getContacto() { return contacto; }
    public void setContacto(String contacto) { this.contacto = contacto; }
    public String getNewPassword() { return newPassword; }
    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
}

package ec.edu.ups.icc.proyectofinal.advice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateAdviceDto {

    @NotBlank(message = "El nombre del solicitante es obligatorio")
    public String nombreUsuario;

    @NotBlank(message = "El teléfono de contacto es obligatorio")
    public String telefono;

    @NotBlank(message = "El mensaje de la consulta no puede estar vacío")
    @Size(min = 10, max = 1000, message = "El mensaje debe tener entre 10 y 1000 caracteres")
    public String mensaje;

    @NotNull(message = "El ID del usuario solicitante es obligatorio")
    public Long usuarioId;

    @NotNull(message = "El ID del programador a consultar es obligatorio")
    public Long programadorId;

    public CreateAdviceDto() {}
}
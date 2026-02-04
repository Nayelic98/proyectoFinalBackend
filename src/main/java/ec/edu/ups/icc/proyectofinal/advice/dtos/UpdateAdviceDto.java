package ec.edu.ups.icc.proyectofinal.advice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class UpdateAdviceDto {

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "aceptada|negada", message = "El estado debe ser 'aceptada' o 'negada'")
    public String estado;

    @NotBlank(message = "Debe proporcionar una justificación o mensaje de respuesta")
    public String mensajeRespuesta;

    public String fecha; // Opcional: Fecha de la cita si se acepta

    public UpdateAdviceDto() {}
}
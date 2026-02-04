package ec.edu.ups.icc.proyectofinal.advice.dtos;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
    "id",
    "nombreUsuario",
    "telefono",
    "mensaje",
    "estado",
    "mensajeRespuesta",
    "fecha",
    "usuario",
    "programador",
    "createdAt"
})
public class AdviceResponseDto {

    public Long id;
    public String nombreUsuario;
    public String telefono;
    public String mensaje;
    public String estado;
    public String mensajeRespuesta;
    public String fecha;
    public LocalDateTime createdAt;

   
    public UserSummaryDto usuario;     
    public UserSummaryDto programador; 

    public AdviceResponseDto() {}

    public static class UserSummaryDto {
        public Long id;
        public String nombre;
        public String contacto;
    }
}

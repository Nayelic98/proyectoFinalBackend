package ec.edu.ups.icc.proyectofinal.exceptions.domain;
 
import ec.edu.ups.icc.proyectofinal.exceptions.base.ApplicationException;
import org.springframework.http.HttpStatus;
public class BadRequestException extends ApplicationException {
    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
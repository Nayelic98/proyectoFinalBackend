package ec.edu.ups.icc.proyectofinal.exceptions.domain;
 
import ec.edu.ups.icc.proyectofinal.exceptions.base.ApplicationException;
import org.springframework.http.HttpStatus;
public class BusinessException extends ApplicationException {

    public BusinessException(String message) {
        super(HttpStatus.UNPROCESSABLE_CONTENT, message);
    }
    protected BusinessException(HttpStatus status, String message) {
        super(status, message);
    }
}
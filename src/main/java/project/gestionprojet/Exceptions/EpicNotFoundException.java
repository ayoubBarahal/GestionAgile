package project.gestionprojet.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EpicNotFoundException extends RuntimeException {


    public EpicNotFoundException(String message) {
        super(message);
    }

}

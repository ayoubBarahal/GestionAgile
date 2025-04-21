package project.gestionprojet.Exceptions;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserExceptions extends RuntimeException {

    public UserExceptions(String message) {
        super(message);
    }

}

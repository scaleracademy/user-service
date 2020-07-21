package dev.naman.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The email ID doesn't matches the token. Please regenerate")
public class IncorrectTokenException extends RuntimeException{
}

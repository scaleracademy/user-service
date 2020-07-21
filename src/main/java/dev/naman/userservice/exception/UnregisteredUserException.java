package dev.naman.userservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The user email ID is not registered")
public class UnregisteredUserException extends RuntimeException{
}

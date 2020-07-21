package dev.naman.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The user is not verified")
public class UnverifiedUserException extends RuntimeException {
}

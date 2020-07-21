package dev.naman.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with the same email ID already exists")
public class DuplicateEmailException  extends RuntimeException {

}

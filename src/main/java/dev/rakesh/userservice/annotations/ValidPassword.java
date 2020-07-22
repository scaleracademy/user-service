package dev.rakesh.userservice.annotations;


import dev.rakesh.userservice.util.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
public @interface ValidPassword {
	String message() default "Passwords should have atleast one Capital Letter, one integer, one special Character";
	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}

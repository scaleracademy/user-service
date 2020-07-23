package dev.rakesh.userservice.util;

import dev.rakesh.userservice.annotations.ValidPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

	private Pattern pattern;
	private Matcher matcher;
	private static final String EMAIL_PATTERN = "^(?=.{6,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*[0-5])(?=.*\\W).*$";


	@Override
	public void initialize(ValidPassword constraintAnnotation) {

	}

	@Override
	public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
		pattern = Pattern.compile(EMAIL_PATTERN);
		matcher = pattern.matcher(password);
		return matcher.matches();
	}
}

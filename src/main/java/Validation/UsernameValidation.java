package Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidation implements ConstraintValidator<UsernameConstraint, String> {

	@Override
	public void initialize(UsernameConstraint con) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return contactField != null && contactField.matches("[0-9a-zA-Z_]{3,16}");
	} 
}
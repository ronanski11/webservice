package Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonValidation implements ConstraintValidator<PersonConstraint, String> {

	@Override
	public void initialize(PersonConstraint contactNumber) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return contactField != null && contactField.matches("[A-Z][a-z]{1,32}");
	} 
}
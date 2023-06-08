package Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidation implements ConstraintValidator<PhoneConstraint, String> {

	@Override
	public void initialize(PhoneConstraint con) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return contactField != null && contactField.matches("^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$");
	} 
}
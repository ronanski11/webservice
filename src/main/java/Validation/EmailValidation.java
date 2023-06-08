package Validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidation implements ConstraintValidator<EmailConstraint, String> {

	@Override
	public void initialize(EmailConstraint con) {
	}

	@Override
	public boolean isValid(String contactField, ConstraintValidatorContext cxt) {
		return contactField != null && contactField.matches("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
	}
}

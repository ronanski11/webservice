package Validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target({ ElementType.METHOD, ElementType.FIELD })

@Retention(RetentionPolicy.RUNTIME)

@Documented

@Constraint(validatedBy = EmailValidation.class)
public @interface EmailConstraint {
	String message() default "Invalid Email.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}

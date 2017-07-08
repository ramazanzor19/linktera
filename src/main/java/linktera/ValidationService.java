package linktera;

import org.apache.commons.validator.routines.EmailValidator;

public class ValidationService {
	
	public boolean validateEmail(final String email) {
		boolean allowLocal = true;
		boolean valid = EmailValidator.getInstance(allowLocal).isValid(email);
		return valid;
	}
}

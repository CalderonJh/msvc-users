package org.calderon.users.util;

public interface CommonValidations {
	/**
	 * To be valid the field must not be null and if the field is a string, it must not be blank
	 *
	 * @param newValue the new value to update
	 * @return true if the field is valid to update, false otherwise
	 */
	static boolean isValidFieldToUpdate(Object newValue) {
		return newValue != null
				&& (!newValue.getClass().equals(String.class) || !((String) newValue).isBlank());
	}
}

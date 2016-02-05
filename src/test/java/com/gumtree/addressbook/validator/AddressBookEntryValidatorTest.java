package com.gumtree.addressbook.validator;

import static org.hamcrest.core.Is.is;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

/**
 * Unit test for {@link AddressBookEntryValidator}.
 */
public class AddressBookEntryValidatorTest {
    @Test
    public void shouldFailValidationWhenInvalidEntryProvided() {
        // Given
        String invalidAddressLine = "abc";
        AddressBookEntryValidator addressBookEntryValidator = new AddressBookEntryValidator();

        // When
        boolean result = addressBookEntryValidator.validate(invalidAddressLine);

        //Then
        MatcherAssert.assertThat("should fail validation for invalid input", result, is(false));
    }

    @Test
    public void shouldSucceedValidationWhenValidEntryProvided() {
        // Given
        String invalidAddressLine = "Bill McKnight, Male, 16/03/77";
        AddressBookEntryValidator addressBookEntryValidator = new AddressBookEntryValidator();

        // When
        boolean result = addressBookEntryValidator.validate(invalidAddressLine);

        //Then
        MatcherAssert.assertThat("should pass validation for valid input", result, is(true));
    }
}
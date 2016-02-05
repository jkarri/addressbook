package com.gumtree.addressbook.validator;

import java.util.regex.Pattern;

/**
 * Class to validate address book entries.
 */
public class AddressBookEntryValidator {
    private static final String ADDRESS_ENTRY_PATTERN_STRING = "\\w+\\s\\w+,\\s(\\bMale\\b|\\bFemale\\b){1},\\s\\d{2}\\/\\d{2}\\/\\d{2}";
    private static final Pattern ADDRESS_ENTRY_PATTERN = Pattern.compile(ADDRESS_ENTRY_PATTERN_STRING);

    public boolean validate(String addressLine) {
        return ADDRESS_ENTRY_PATTERN.matcher(addressLine).matches();
    }
}

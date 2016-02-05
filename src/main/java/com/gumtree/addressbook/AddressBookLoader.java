package com.gumtree.addressbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.gumtree.addressbook.exception.InvalidAddressBookException;
import com.gumtree.addressbook.validator.AddressBookEntryValidator;

/**
 * Service class to load the address book entries from address book file.
 */
public class AddressBookLoader {

    private List<String> addressEntries;
    private AddressBookEntryValidator addressBookEntryValidator;

    /**
     * Default constructor.
     * @param filePath given file path of the address book
     */
    public AddressBookLoader(String filePath, AddressBookEntryValidator addressBookEntryValidator) throws IOException {
        Preconditions.checkArgument(filePath != null && !filePath.isEmpty(), "File path cannot be null/empty");
        this.addressBookEntryValidator = addressBookEntryValidator;
        this.addressEntries = getAddressBookEntries(filePath);

    }

    private void validateAddressEntries(List<String> addressEntries) throws InvalidAddressBookException {
        if (addressEntries.size() > 0 && !addressEntries.stream().allMatch(entry -> addressBookEntryValidator.validate(entry))) {
            throw new InvalidAddressBookException("Address book with invalid entries provided");
        }
    }

    private List<String> getAddressBookEntries(String filePath) throws IOException {
        List<String> lines;
        try (BufferedReader bufferedReader = new BufferedReader(openFile(filePath))) {
            lines = bufferedReader.lines().collect(Collectors.toList());
        }

        return lines;
    }

    private FileReader openFile(String filePath) throws FileNotFoundException {
        return new FileReader(new File(filePath));
    }

    public List<String> getAddressEntries() throws InvalidAddressBookException {
        validateAddressEntries(this.addressEntries);

        return addressEntries;
    }
}

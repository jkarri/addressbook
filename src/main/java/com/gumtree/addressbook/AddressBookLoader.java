package com.gumtree.addressbook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

/**
 * Service class to load the address book entries from address book file.
 */
public class AddressBookLoader {

    private List<String> addressEntries;

    /**
     * Default constructor.
     * @param filePath given file path of the address book
     */
    public AddressBookLoader(String filePath) throws IOException {
        Preconditions.checkArgument(filePath != null && !filePath.isEmpty(), "File path cannot be null/empty");
        this.addressEntries = getAddressBookEntries(filePath);

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

    public List<String> getAddressEntries() {
        return addressEntries;
    }
}

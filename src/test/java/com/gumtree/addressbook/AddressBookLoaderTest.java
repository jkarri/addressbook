package com.gumtree.addressbook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Unit test {@link AddressBookLoader}.
 */
public class AddressBookLoaderTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFilePathIsEmpty() throws IOException {
        // Given
        String filePath = "";

        // When
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath);

        //Then verify that IllegalArgumentException is thrown
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldThrowExceptionWhenFileDoesNotExist() throws IOException {
        // Given
        String filePath = "src/test/resources/MissingAddressBook";

        // When
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath);

        //Then verify that FileNotFoundException is thrown
    }

    @Test
    public void addressBookEntriesShouldBeEmptyWhenGivenFileIsEmpty() throws IOException {
        // Given
        String filePath = "src/test/resources/EmptyAddressBook";
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath);

        // When
        List<String> addressEntries = addressBookLoader.getAddressEntries();

        //Then
        assertThat("Address book entries should be zero for an empty file", addressEntries.isEmpty(), is(true));
    }

    @Test
    public void addressBookEntriesShouldBeNonEmptyWhenGivenFileHasEntries() throws IOException {
        // Given
        String filePath = "src/test/resources/AddressBook";
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath);

        // When
        List<String> addressEntries = addressBookLoader.getAddressEntries();

        //Then
        assertThat("Address book should have entries", addressEntries.isEmpty(), is(false));
    }
}
package com.gumtree.addressbook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.testng.annotations.Test;

/**
 * Unit test {@link AddressBookService}.
 */
public class AddressBookServiceTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFilePathIsEmpty() throws IOException {
        // Given
        String filePath = "";

        // When
        AddressBookService addressBookService = new AddressBookService(filePath);

        //Then verify that IllegalArgumentException is thrown
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldThrowExceptionWhenFileDoesNotExist() throws IOException {
        // Given
        String filePath = "src/test/resources/MissingAddressBook";

        // When
        AddressBookService addressBookService = new AddressBookService(filePath);

        //Then verify that FileNotFoundException is thrown
    }

    @Test
    public void addressBookEntriesShouldBeEmptyWhenGivenFileIsEmpty() throws IOException {
        // Given
        String filePath = "src/test/resources/EmptyAddressBook";
        AddressBookService addressBookService = new AddressBookService(filePath);

        // When
        List<String> addressEntries = addressBookService.getAddressEntries();

        //Then
        assertThat("Address book entries should be zero for an empty file", addressEntries.isEmpty(), is(true));
    }

    @Test
    public void addressBookEntriesShouldBeNonEmptyWhenGivenFileHasEntries() throws IOException {
        // Given
        String filePath = "src/test/resources/AddressBook";
        AddressBookService addressBookService = new AddressBookService(filePath);

        // When
        List<String> addressEntries = addressBookService.getAddressEntries();

        //Then
        assertThat("Address book should have entries", addressEntries.isEmpty(), is(false));
    }
}
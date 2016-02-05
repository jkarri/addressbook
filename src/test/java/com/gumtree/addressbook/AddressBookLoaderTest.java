package com.gumtree.addressbook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.gumtree.addressbook.exception.InvalidAddressBookException;
import com.gumtree.addressbook.validator.AddressBookEntryValidator;

/**
 * Unit test {@link AddressBookLoader}.
 */
public class AddressBookLoaderTest {

    @Mock
    private AddressBookEntryValidator addressBookEntryValidator;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenFilePathIsEmpty() throws IOException, InvalidAddressBookException {
        // Given
        String filePath = "";

        // When
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath, addressBookEntryValidator);

        //Then verify that IllegalArgumentException is thrown
    }

    @Test(expectedExceptions = FileNotFoundException.class)
    public void shouldThrowExceptionWhenFileDoesNotExist() throws IOException, InvalidAddressBookException {
        // Given
        String filePath = "src/test/resources/MissingAddressBook";

        // When
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath, addressBookEntryValidator);

        //Then verify that FileNotFoundException is thrown
    }

    @Test
    public void addressBookEntriesShouldBeEmptyWhenGivenFileIsEmpty() throws IOException, InvalidAddressBookException {
        // Given
        String filePath = "src/test/resources/EmptyAddressBook";
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath, addressBookEntryValidator);

        // When
        List<String> addressEntries = addressBookLoader.getAddressEntries();

        //Then
        assertThat("Address book entries should be zero for an empty file", addressEntries.isEmpty(), is(true));
    }

    @Test
    public void addressBookEntriesShouldBeNonEmptyWhenGivenFileHasEntries() throws IOException, InvalidAddressBookException {
        // Given
        String filePath = "src/test/resources/AddressBook";
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath, addressBookEntryValidator);

        // When
        List<String> addressEntries = addressBookLoader.getAddressEntries();

        //Then
        assertThat("Address book should have entries", addressEntries.isEmpty(), is(false));
    }

    @Test(expectedExceptions = InvalidAddressBookException.class)
    public void shouldValidateAndThrowExceptionWhenAddressWithInvalidEntryProvided() throws IOException, InvalidAddressBookException {
        // Given
        String filePath = "src/test/resources/InvalidAddressBook";
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath, addressBookEntryValidator);

        // validation fails
        given(addressBookEntryValidator.validate(Mockito.anyString())).willReturn(false);

        // When
        addressBookLoader.getAddressEntries();

        //Then verify that InvalidAddressBookException
    }

    @Test
    public void shouldValidateAndSucceedWhenAllEntriesAreValid() throws IOException, InvalidAddressBookException {
        // Given
        String filePath = "src/test/resources/AddressBook";
        AddressBookLoader addressBookLoader = new AddressBookLoader(filePath, addressBookEntryValidator);

        // validation passes
        given(addressBookEntryValidator.validate(Mockito.anyString())).willReturn(true);

        // When
        List<String> addressEntries = addressBookLoader.getAddressEntries();

        //Then
        assertThat(addressEntries.size(), is(5));
    }
}
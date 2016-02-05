package com.gumtree.addressbook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.gumtree.addressbook.domain.Person;
import com.gumtree.addressbook.exception.InvalidAddressBookException;

/**
 * Unit test for {@link AddressBookProvider}.
 */
public class AddressBookProviderTest {
    @Mock
    private AddressBookLoader addressBookLoader;
    @Mock
    private AddressBookEntryPersonTransformer addressBookEntryPersonTransformer;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldLoadAddressBook() throws InvalidAddressBookException {
        // Given
        AddressBookProvider addressBookProvider = new AddressBookProvider(addressBookLoader, addressBookEntryPersonTransformer);

        // When
        addressBookProvider.readAddressBook();

        //Then
        verify(addressBookLoader).getAddressEntries();
    }

    @Test
    public void shouldTransformAddressEntriesToPersons() throws InvalidAddressBookException {
        // Given
        AddressBookProvider addressBookProvider = new AddressBookProvider(addressBookLoader, addressBookEntryPersonTransformer);

        // Address book entries exist
        List<String> entries = Lists.newArrayList("entry1", "entry2");
        given(addressBookLoader.getAddressEntries()).willReturn(entries);
        given(addressBookEntryPersonTransformer.transform(Mockito.anyString())).willReturn(Mockito.mock(Person.class));

        // When
        List<Person> persons = addressBookProvider.readAddressBook();

        //Then
        assertThat("persons should be transformed and returned", persons.isEmpty(), is(false));
    }

}
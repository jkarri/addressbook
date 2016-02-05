package com.gumtree.addressbook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.List;

import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.google.common.collect.Lists;
import com.gumtree.addressbook.domain.Person;

/**
 * Unit test for {@link AddressBookService}.
 */
public class AddressBookServiceTest {

    @Mock
    private AddressBookProvider addressBookProvider;

    @InjectMocks
    private AddressBookService addressBookService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldGetPersons() {
        // Given
        List<Person> persons = Lists.newArrayList();
        BDDMockito.given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        List<Person> actualPersons = addressBookService.getPersons();

        //Then
        assertThat("should read the persons from address book provider", actualPersons, is(persons));
    }
}
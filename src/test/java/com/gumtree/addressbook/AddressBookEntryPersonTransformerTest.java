package com.gumtree.addressbook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

import java.time.LocalDate;

import org.testng.annotations.Test;

import com.gumtree.addressbook.domain.Gender;
import com.gumtree.addressbook.domain.Person;

/**
 * Unit test for {@link AddressBookEntryPersonTransformer}.
 */
public class AddressBookEntryPersonTransformerTest {

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void shouldThrowExceptionWhenEntryIsEmpty() {
        // Given
        String entry = "";
        AddressBookEntryPersonTransformer addressBookEntryPersonTransformer = new AddressBookEntryPersonTransformer();

        // When
        Person person = addressBookEntryPersonTransformer.transform(entry);

        //Then verify that IllegalArgumentException is thrown
    }

    @Test
    public void shouldTransformEntryToPerson() {
        // Given
        String entry = "Bill McKnight, Male, 16/03/77";
        AddressBookEntryPersonTransformer addressBookEntryPersonTransformer = new AddressBookEntryPersonTransformer();

        // When
        Person person = addressBookEntryPersonTransformer.transform(entry);

        //Then
        assertThat("person is expected to be transformed", person, is(notNullValue()));
    }

    @Test
    public void shouldTransformNameOfThePerson() {
        // Given
        String entry = "Bill McKnight, Male, 16/03/77";
        AddressBookEntryPersonTransformer addressBookEntryPersonTransformer = new AddressBookEntryPersonTransformer();

        // When
        Person person = addressBookEntryPersonTransformer.transform(entry);

        //Then
        assertThat("persons name should be Bill McKnight", person.getName(), is("Bill McKnight"));
    }

    @Test
    public void shouldTransformGenderOfThePerson() {
        // Given
        String entry = "Bill McKnight, Male, 16/03/77";
        AddressBookEntryPersonTransformer addressBookEntryPersonTransformer = new AddressBookEntryPersonTransformer();

        // When
        Person person = addressBookEntryPersonTransformer.transform(entry);

        //Then
        assertThat("persons gender should be Male", person.getGender(), is(Gender.MALE));
    }

    @Test
    public void shouldTransformDateOfBirthOfThePerson() {
        // Given
        String entry = "Bill McKnight, Male, 16/03/77";
        AddressBookEntryPersonTransformer addressBookEntryPersonTransformer = new AddressBookEntryPersonTransformer();

        // When
        Person person = addressBookEntryPersonTransformer.transform(entry);

        //Then
        assertThat("persons date of birth should be 16th March 1977", person.getDateOfBirth(), is(LocalDate.of(1977, 3, 16)));
    }
}
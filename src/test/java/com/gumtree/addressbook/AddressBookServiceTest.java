package com.gumtree.addressbook;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;

import static com.gumtree.addressbook.domain.Gender.FEMALE;
import static com.gumtree.addressbook.domain.Gender.MALE;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
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
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        List<Person> actualPersons = addressBookService.getPersons();

        //Then
        assertThat("should read the persons from address book provider", actualPersons, is(persons));
    }

    @Test
    public void numberOfMalesShouldBeZeroForAnEmptyAddressBook() {
        // Given
        List<Person> persons = Lists.newArrayList();
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        long numberOfMales = addressBookService.getNumberOfMales();

        //Then
        assertThat("number of males should be zero for an empty address book file", numberOfMales, is(0L));
    }

    @Test
    public void numberOfMalesShouldBeZeroWhenASingleAddressEntryWithFemaleExists() {
        // Given
        Person malePerson = Mockito.mock(Person.class);
        given(malePerson.getGender()).willReturn(FEMALE);
        List<Person> persons = Lists.newArrayList(malePerson);
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        long numberOfMales = addressBookService.getNumberOfMales();

        //Then
        assertThat("number of males should be zero for an address book with one female", numberOfMales, is(0L));
    }

    @Test
    public void numberOfMalesShouldBeOneWhenASingleAddressEntryWithMaleExists() {
        // Given
        Person malePerson = Mockito.mock(Person.class);
        given(malePerson.getGender()).willReturn(MALE);
        List<Person> persons = Lists.newArrayList(malePerson);
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        long numberOfMales = addressBookService.getNumberOfMales();

        //Then
        assertThat("number of males should be one for an address book with one male", numberOfMales, is(1L));
    }

    @Test
    public void numberOfMalesShouldBeOneWhenTwoAddressEntriesWithMaleAndFemaleEachExist() {
        // Given a male person
        Person malePerson = Mockito.mock(Person.class);
        given(malePerson.getGender()).willReturn(MALE);
        // and a female entry
        Person femalePerson = Mockito.mock(Person.class);
        given(femalePerson.getGender()).willReturn(FEMALE);

        List<Person> persons = Lists.newArrayList(malePerson, femalePerson);
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        long numberOfMales = addressBookService.getNumberOfMales();

        //Then
        assertThat("number of males should be one for an address book with one male and female is provided", numberOfMales, is(1L));
    }

    @Test
    public void numberOfMalesShouldBeTwoWhenTwoAddressEntriesWithMaleExist() {
        // Given a male person
        Person malePerson1 = Mockito.mock(Person.class);
        given(malePerson1.getGender()).willReturn(MALE);
        // and a female entry
        Person malePerson2 = Mockito.mock(Person.class);
        given(malePerson2.getGender()).willReturn(MALE);

        List<Person> persons = Lists.newArrayList(malePerson1, malePerson2);
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        long numberOfMales = addressBookService.getNumberOfMales();

        //Then
        assertThat("number of males should be two for an address book with two males", numberOfMales, is(2L));
    }

    @Test
    public void oldestPersonShouldBeAbsentWhenTheAddressBookIsEmpty() {
        // Given the address book is empty
        List<Person> persons = Lists.newArrayList();
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        Optional<Person> oldest = addressBookService.oldestPerson();

        //Then
        assertThat("oldest person should be the one entry in the address book", oldest.isPresent(), is(false));
    }

    @Test
    public void oldestPersonShouldBeTheGivenUserWhenOnlyOneEntryExists() {
        // Given the address book has one person
        Person person = Mockito.mock(Person.class);
        List<Person> persons = Lists.newArrayList(person);
        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        Optional<Person> oldest = addressBookService.oldestPerson();

        //Then
        assertThat("oldest person should be the one entry in the address book", oldest.get(), is(person));
    }

    @Test
    public void oldestPersonShouldBeTheOlderOfTheTwoGivenEntries() {
        // Given an old person
        Person old = Mockito.mock(Person.class);
        given(old.getDateOfBirth()).willReturn(LocalDate.now());
        // And an older person
        Person older = Mockito.mock(Person.class);
        given(older.getDateOfBirth()).willReturn(LocalDate.now().minusDays(2));
        List<Person> persons = Lists.newArrayList(old, older);

        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        Optional<Person> oldest = addressBookService.oldestPerson();

        //Then
        assertThat("oldest should be the older of the two", oldest.get(), is(older));
    }

    @Test
    public void oldestPersonShouldBeTheOldestOfAll() {
        // Given an old person
        Person old = Mockito.mock(Person.class);
        given(old.getDateOfBirth()).willReturn(LocalDate.now());
        // And and older person
        Person older = Mockito.mock(Person.class);
        given(older.getDateOfBirth()).willReturn(LocalDate.now().minusDays(2));
        // And and older person
        Person oldest = Mockito.mock(Person.class);
        given(oldest.getDateOfBirth()).willReturn(LocalDate.now().minusDays(10));

        List<Person> persons = Lists.newArrayList(old, older, oldest);

        given(addressBookProvider.readAddressBook()).willReturn(persons);

        // When
        Optional<Person> actualOldest = addressBookService.oldestPerson();

        //Then
        assertThat("oldest should be the oldest of all", actualOldest.get(), is(oldest));
    }
}
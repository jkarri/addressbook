package com.gumtree.addressbook;

import static java.lang.Math.abs;
import static java.text.MessageFormat.format;

import static com.gumtree.addressbook.domain.Gender.MALE;

import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import com.google.common.collect.Maps;
import com.gumtree.addressbook.domain.Person;
import com.gumtree.addressbook.exception.InvalidAddressBookException;
import com.gumtree.addressbook.exception.PersonNotFoundException;
import com.gumtree.addressbook.validator.AddressBookEntryValidator;

/**
 * Service class to enable operations on address book entries.
 */
public class AddressBookService {

    private AddressBookProvider addressBookProvider;

    /**
     * Inject dependencies.
     * @param addressBookProvider     {@link AddressBookProvider}
     */
    public AddressBookService(AddressBookProvider addressBookProvider) {
        this.addressBookProvider = addressBookProvider;
    }

    public List<Person> getPersons() throws InvalidAddressBookException {
        return addressBookProvider.readAddressBook();
    }

    public long getNumberOfMales() throws InvalidAddressBookException {
        return getPersons().stream().filter(isMale()).count();
    }

    private Predicate<Person> isMale() {
        return person -> person.getGender() == MALE;
    }

    public Optional<Person> oldestPerson() throws InvalidAddressBookException {
        return getPersons().stream()
                .sorted(dateComparator())
                .findFirst();
    }

    private Comparator<Person> dateComparator() {
        return (p1, p2) -> p1.getDateOfBirth().compareTo(p2.getDateOfBirth());
    }

    public long ageDifferenceInDays(String name1, String name2) throws PersonNotFoundException, InvalidAddressBookException {
        Map<String, Person> personsByName = getPersonsByName();
        validateEntriesExist(name1, name2, personsByName);
        Person person1 = personsByName.get(name1);
        Person person2 = personsByName.get(name2);

        return abs(ChronoUnit.DAYS.between(person1.getDateOfBirth(), person2.getDateOfBirth()));
    }

    private void validateEntriesExist(String name1, String name2, Map<String, Person> personsByName) throws PersonNotFoundException {
        if (!personsByName.containsKey(name1) || !personsByName.containsKey(name2)) {
            throw new PersonNotFoundException(format("One or both persons with the given names ({0}, {1}) is not found", name1, name2));
        }
    }

    private Map<String, Person> getPersonsByName() throws InvalidAddressBookException {
        return Maps.uniqueIndex(getPersons(), Person::getName);
    }

    public static void main(String[] args) throws IOException, InvalidAddressBookException, PersonNotFoundException {
        AddressBookEntryValidator addressBookEntryValidator = new AddressBookEntryValidator();
        AddressBookLoader addressBookLoader = new AddressBookLoader("src/main/resources/AddressBook", addressBookEntryValidator);
        AddressBookEntryPersonTransformer addressBookEntryPersonTransformer = new AddressBookEntryPersonTransformer();
        AddressBookProvider addressBookProvider = new AddressBookProvider(addressBookLoader, addressBookEntryPersonTransformer);
        AddressBookService addressBookService = new AddressBookService(addressBookProvider);

        System.out.println("Number of males are : " + addressBookService.getNumberOfMales());
        System.out.println("Oldest person is : " + addressBookService.oldestPerson());
        System.out.println("How many days is Bill Older than Paul : " + addressBookService.ageDifferenceInDays("Bill McKnight", "Paul Robinson"));

    }
}

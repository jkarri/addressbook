package com.gumtree.addressbook;

import static com.gumtree.addressbook.domain.Gender.MALE;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import com.gumtree.addressbook.domain.Person;

/**
 * Service class to enable operations on address book entries.
 */
public class AddressBookService {

    private AddressBookProvider addressBookProvider;

    /**
     * Inject dependencies.
     * @param addressBookLoader     {@link AddressBookLoader}
     */
    public AddressBookService(AddressBookProvider addressBookProvider, AddressBookLoader addressBookLoader, AddressBookEntryPersonTransformer addressBookEntryPersonTransformer) {
        this.addressBookProvider = addressBookProvider;
    }

    public List<Person> getPersons() {
        return addressBookProvider.readAddressBook();
    }

    public long getNumberOfMales() {
        return getPersons().stream().filter(isMale()).count();
    }

    private Predicate<Person> isMale() {
        return person -> person.getGender() == MALE;
    }

    public Optional<Person> oldestPerson() {
        return getPersons().stream()
                .sorted(dateComparator())
                .findFirst();
    }

    private Comparator<Person> dateComparator() {
        return (p1, p2) -> p1.getDateOfBirth().compareTo(p2.getDateOfBirth());
    }
}

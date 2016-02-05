package com.gumtree.addressbook;

import static com.gumtree.addressbook.domain.Gender.MALE;

import java.util.List;

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
        return getPersons().stream().filter(person -> person.getGender() == MALE).count();
    }
}

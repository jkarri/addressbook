package com.gumtree.addressbook;

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
}

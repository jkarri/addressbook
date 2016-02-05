package com.gumtree.addressbook;

import java.util.List;

import com.google.common.collect.Lists;
import com.gumtree.addressbook.domain.Person;

/**
 * Service class to enable operations on address book entries.
 */
public class AddressBookService {

    private AddressBookLoader addressBookLoader;
    private AddressBookEntryPersonTransformer addressBookEntryPersonTransformer;

    /**
     * Inject dependencies.
     * @param addressBookLoader     {@link AddressBookLoader}
     */
    public AddressBookService(AddressBookLoader addressBookLoader, AddressBookEntryPersonTransformer addressBookEntryPersonTransformer) {
        this.addressBookLoader = addressBookLoader;
        this.addressBookEntryPersonTransformer = addressBookEntryPersonTransformer;
    }

    public List<Person> readAddressBook() {
        List<String> addressEntries = addressBookLoader.getAddressEntries();
        return Lists.transform(addressEntries, entry -> addressBookEntryPersonTransformer.transform(entry));
    }
}

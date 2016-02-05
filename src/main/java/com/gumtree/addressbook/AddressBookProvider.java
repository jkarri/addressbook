package com.gumtree.addressbook;

import java.util.List;

import com.google.common.collect.Lists;
import com.gumtree.addressbook.domain.Person;
import com.gumtree.addressbook.exception.InvalidAddressBookException;

/**
 * Class to load address book and transform to {@link com.gumtree.addressbook.domain.Person}.
 */
public class AddressBookProvider {

    private AddressBookLoader addressBookLoader;
    private AddressBookEntryPersonTransformer addressBookEntryPersonTransformer;

    /**
     * Inject dependencies.
     * @param addressBookLoader                     {@link AddressBookLoader}
     * @param addressBookEntryPersonTransformer     {@link AddressBookEntryPersonTransformer}
     */
    public AddressBookProvider(AddressBookLoader addressBookLoader, AddressBookEntryPersonTransformer addressBookEntryPersonTransformer) {
        this.addressBookLoader = addressBookLoader;
        this.addressBookEntryPersonTransformer = addressBookEntryPersonTransformer;
    }

    public List<Person> readAddressBook() throws InvalidAddressBookException {
        List<String> addressEntries = addressBookLoader.getAddressEntries();
        return Lists.transform(addressEntries, entry -> addressBookEntryPersonTransformer.transform(entry));
    }

}

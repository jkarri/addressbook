package com.gumtree.addressbook.exception;

/**
 * Exception to denote that a person with a given name has not been found in the address book.
 */
public class PersonNotFoundException extends Exception {

    /**
     * Default constructor.
     * @param message custom error message
     */
    public PersonNotFoundException(String message) {
        super(message);
    }
}

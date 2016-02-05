package com.gumtree.addressbook.exception;

/**
 * Exception to denote that the given address book is invalid.
 */
public class InvalidAddressBookException extends Exception {

    /**
     * Default constructor.
     * @param message exception message
     */
    public InvalidAddressBookException(String message) {
        super(message);
    }
}

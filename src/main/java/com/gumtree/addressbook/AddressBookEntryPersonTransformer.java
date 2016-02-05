package com.gumtree.addressbook;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;
import com.gumtree.addressbook.domain.Gender;
import com.gumtree.addressbook.domain.Person;

/**
 * Transform an address entry to a {@link com.gumtree.addressbook.domain.Person}.
 */
public class AddressBookEntryPersonTransformer {

    public static final DateTimeFormatter DATE_PATTERN = DateTimeFormatter.ofPattern("dd/MM/yy");

    public Person transform(String entry) {
        Preconditions.checkArgument(entry != null && !entry.isEmpty(), "Entry cannot be empty/null");

        List<String> tokens = Arrays.stream(entry.split(",")).map(String::trim).collect(Collectors.toList());

        return new Person(tokens.get(0), Gender.valueOf(tokens.get(1).toUpperCase()), LocalDate.parse(tokens.get(2), DATE_PATTERN));
    }

}

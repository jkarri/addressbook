package com.gumtree.addressbook;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
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

    /**
     * Transform an address line to {@link Person}.
     * @param entry address book line
     * @return transformed person
     */
    public Person transform(String entry) {
        Preconditions.checkArgument(entry != null && !entry.isEmpty(), "Entry cannot be empty/null");

        List<String> tokens = Arrays.stream(entry.split(",")).map(String::trim).collect(Collectors.toList());

        DateTimeFormatter dateFormatter = getDateFormatter();
        return new Person(tokens.get(0), Gender.valueOf(tokens.get(1).toUpperCase()), LocalDate.parse(tokens.get(2), dateFormatter));
    }

    /**
     * Java 8 uses the range 2000-2009 by default and not the relative -80 +20 relative to today, hence we need to reduce the year as below.
     * @return date formatter with year reduced by 80 years.
     */
    private DateTimeFormatter getDateFormatter() {
        return new DateTimeFormatterBuilder()
                .appendPattern("dd/MM/")
                .appendValueReduced(ChronoField.YEAR, 2, 2, Year.now().getValue() - 80)
                .toFormatter();
    }

}

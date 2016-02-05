package com.gumtree.addressbook.domain;

import java.time.LocalDate;

import com.google.common.base.Objects;

/**
 * Class to represent a {@link Person}.
 */
public class Person {
    private final String name;
    private final Gender gender;
    private final LocalDate dateOfBirth;

    public Person(String name, Gender gender, LocalDate dateOfBirth) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        return name != null ? name.equals(person.name) : person.name == null && gender == person.gender && (dateOfBirth != null ? dateOfBirth.equals(person.dateOfBirth) : person.dateOfBirth == null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (gender != null ? gender.hashCode() : 0);
        result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("name", name)
                .add("gender", gender)
                .add("dateOfBirth", dateOfBirth)
                .toString();
    }
}

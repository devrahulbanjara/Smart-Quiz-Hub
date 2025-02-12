package com.main_system;

/**
 * The Name class represents a person's full name and provides methods to retrieve the full name,
 * initials, and set/get individual name components (first name, middle name, last name).
 */
public class Name {
    private String firstName;
    private String middleName;
    private String lastName;

    /**
     * Constructs a Name object from a full name string. The full name is split into first, middle, and last names.
     * @param fullName The full name string.
     */
    public Name(String fullName) {
        String[] nameParts = fullName.split(" ");

        if (nameParts.length > 0) {
            this.firstName = nameParts[0];
        } else {
            this.firstName = "";
        }

        if (nameParts.length > 2) {
            this.middleName = nameParts[1];
        } else {
            this.middleName = "";
        }

        if (nameParts.length > 1) {
            this.lastName = nameParts[nameParts.length - 1];
        } else {
            this.lastName = "";
        }
    }

    /**
     * Gets the first name of the person.
     * @return The first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the person.
     * @param firstName The first name to set.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the middle name of the person.
     * @return The middle name.
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * Sets the middle name of the person.
     * @param middleName The middle name to set.
     */
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    /**
     * Gets the last name of the person.
     * @return The last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the person.
     * @param lastName The last name to set.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the full name by concatenating the first name, middle name, and last name.
     * @return The full name.
     */
    public String getFullName() {
        StringBuilder fullName = new StringBuilder(firstName);

        if (!middleName.isEmpty()) {
            fullName.append(" ").append(middleName);
        }

        if (!lastName.isEmpty()) {
            fullName.append(" ").append(lastName);
        }

        return fullName.toString();
    }

    /**
     * Gets the initials of the person by taking the first letter of the first name, middle name, and last name.
     * @return The initials.
     */
    public String getInitials() {
        StringBuilder initials = new StringBuilder();

        if (!firstName.isEmpty()) {
            initials.append(firstName.charAt(0));
        }

        if (!middleName.isEmpty()) {
            initials.append(middleName.charAt(0));
        }

        if (!lastName.isEmpty()) {
            initials.append(lastName.charAt(0));
        }

        return initials.toString();
    }
}

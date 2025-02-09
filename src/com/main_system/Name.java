package com.main_system;

public class Name {
    private String firstName;
    private String middleName;
    private String lastName;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

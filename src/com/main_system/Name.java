package com.main_system;

public class Name {
    private String firstName;
    private String middleName;
    private String lastName;

    public Name(String fullName) {
        // Split the full name into parts: first, middle, and last
        String[] nameParts = fullName.split(" ");
        
        this.firstName = nameParts[0];
        this.middleName = (nameParts.length > 2) ? nameParts[1] : "";  // Middle name is empty if not available
        this.lastName = (nameParts.length > 1) ? nameParts[nameParts.length - 1] : ""; // Last name is the last part
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
        return firstName + " " + middleName + " " + lastName;
    }

    public String getInitials() {
        StringBuilder initials = new StringBuilder();

        // Ensure first name is not empty before accessing its first character
        if (firstName != null && !firstName.isEmpty()) {
            initials.append(firstName.charAt(0));
        }

        // Ensure middle name is not empty before accessing its first character
        if (middleName != null && !middleName.isEmpty()) {
            initials.append(middleName.charAt(0));
        }

        // Ensure last name is not empty before accessing its first character
        if (lastName != null && !lastName.isEmpty()) {
            initials.append(lastName.charAt(0));
        }

        return initials.toString();
    }

}

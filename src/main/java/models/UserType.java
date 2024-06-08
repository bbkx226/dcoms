package models;

// Enum representing different types of users
public enum UserType {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String stringValue;

    // Constructor to initialize the string value of the enum
    UserType(String stringValue) {
        this.stringValue = stringValue;
    }

    // Returns the string value of the enum
    public String getStringValue() {
        return stringValue;
    }
}
package models;

public enum UserType {
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER");

    private final String stringValue;

    UserType(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }
}

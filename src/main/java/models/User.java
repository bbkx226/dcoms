package models;

import java.io.Serializable;

// Represents a user in the system
public class User implements Serializable {
    private final int id;
    private final UserType userType;
    private String firstName;
    private String lastName;
//    private final String ICNum;
    private String ICNum;
    private final String username;
    private String password;

    // Constructor to initialize the user
    public User(int id, UserType userType, String firstName, String lastName, String ICNum, String username, String password) {
        this.id = id;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ICNum = ICNum;
        this.username = username;
        this.password = password;
    }

    // Getters and setters
    public int getId() { return id; }
    public UserType getUserType() { return userType; }
    public String getUserTypeString() { return userType.getStringValue(); }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getICNum() { return ICNum; }
    public void setICNum(String ICNum) { this.ICNum = ICNum; }

    public String getUsername() { return username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Converts the user to a string
    @Override
    public String toString() {
        return String.format("%d,%s,%s,%s,%s,%s,%s",
                id, userType.getStringValue(), firstName, lastName, ICNum, username, password);
    }

    // Creates a user from a string
    public static User fromString(String userString) {
        String[] parts = userString.split(",");
        int id = Integer.parseInt(parts[0]);
        UserType userType = UserType.valueOf(parts[1]);
        return new User(id, userType, parts[2], parts[3], parts[4], parts[5], parts[6]);
    }
}
package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import models.User;

// Provides remote user service
public interface UserServiceRemote extends Remote {
    // Adds a new user
    boolean addUser(String newFirstName, String newLastName, String newICNum, String newUsername, String newPassword) throws RemoteException;

    // Returns a list of all users
    List<User> getAllUsers() throws RemoteException;

    // Returns a user by their ID
    User getUserById(int userId) throws RemoteException;

    // Updates an existing user
    boolean updateUser(User updatedUser) throws RemoteException;

    // Removes a user
    boolean removeUser(User userToRemove) throws RemoteException;
}
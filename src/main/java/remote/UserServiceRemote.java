package remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import models.User;

// Provides remote user service
public interface UserServiceRemote extends Remote {
    boolean addUser(String newFirstName, String newLastName, String newICNum, String newUsername, String newPassword) throws RemoteException; // Adds a new user
    List<User> getAllUsers() throws RemoteException; // Returns a list of all users
    User getUserById(int userId) throws RemoteException; // Returns a user by their ID
    boolean updateUser(User updatedUser) throws RemoteException; // Updates an existing user    
    boolean removeUser(User userToRemove) throws RemoteException; // Removes a user
}
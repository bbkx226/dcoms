package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import models.User;
import models.UserType;
import remote.UserServiceRemote;
import utils.FileUtils;

// Provides CRUD operations for User objects
public class UserServiceImpl extends UnicastRemoteObject implements UserServiceRemote {
    public UserServiceImpl() throws RemoteException {
        super();
    }

    // Adds a new user if the username is not already taken
    @Override
    public boolean addUser(String newFirstName, String newLastName, String newICNum, String newUsername, String newPassword) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        if (users.stream().anyMatch(user -> newUsername.equals(user.getUsername()))) {
            return false;
        }
        int newId = users.size() + 1;
        UserType newUserType = UserType.CUSTOMER;
        User newUser = new User(newId, newUserType, newFirstName, newLastName, newICNum, newUsername, newPassword);
        FileUtils.appendToFile(FileUtils.FileType.USER, newUser, User::toString);
        return true;
    }

    // Returns a list of all users
    @Override
    public List<User> getAllUsers() throws RemoteException {
        return FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
    }

    // Returns a user by their ID, or null if no such user exists
    @Override
    public User getUserById(int userId) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        return users.size() >= userId ? users.get(userId - 1) : null;
    }

    // Updates an existing user if they exist
    @Override
    public boolean updateUser(User updatedUser) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        if (users.size() >= updatedUser.getId()) {
            users.set(updatedUser.getId() - 1, updatedUser);
            FileUtils.updateFile(FileUtils.FileType.USER, users, User::toString);
            return true;
        } else return false;
    }

    // Removes a user if they are not an admin and they exist
    @Override
    public boolean removeUser(User userToRemove) throws RemoteException {
        if (userToRemove.getUserType().equals(UserType.ADMIN) || getUserById(userToRemove.getId()) == null) {
            return false;
        }
        FileUtils.deleteFromFile(FileUtils.FileType.USER, userToRemove.getId());
        return true;
    }

    // Check a new user if the username is not exist or not
    @Override
    public boolean checkUserName(String newUsername) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        return users.stream().anyMatch(user -> newUsername.equals(user.getUsername()));
    }

}
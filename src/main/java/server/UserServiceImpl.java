package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.User;
import models.UserType;
import remote.UserServiceRemote;
import utils.FileUtils;

// Provides CRUD operations for User objects
public class UserServiceImpl extends UnicastRemoteObject implements UserServiceRemote {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    public UserServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean addUser(String newFirstName, String newLastName, String newICNum, String newUsername, String newPassword) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        int maxUserId = users.isEmpty() ? 0 : users.stream().mapToInt(User::getId).max().orElse(0);
        int newId = maxUserId + 1;
        if (users.stream().anyMatch(user -> newUsername.equals(user.getUsername()))) {
            LOGGER.log(Level.INFO, String.format("Add user failed: Username %s already exists", newUsername));
            return false;
        }
        UserType newUserType = UserType.CUSTOMER;
        User newUser = new User(newId, newUserType, newFirstName, newLastName, newICNum, newUsername, newPassword);
        FileUtils.appendToFile(FileUtils.FileType.USER, newUser, User::toString);
        LOGGER.log(Level.INFO, String.format("User added successfully: %s", newUsername));
        return true;
    }
    
    @Override
    public List<User> getAllUsers() throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        LOGGER.log(Level.INFO, "Retrieved all users");
        return users;
    }
    
    @Override
    public User getUserById(int userId) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        for (User user : users) {
            if (user.getId() == userId) {
                LOGGER.log(Level.INFO, String.format("User retrieved by ID: %d", userId));
                return user;
            }
        }
        LOGGER.log(Level.INFO, String.format("User not found by ID: %d", userId));
        return null;
    }
    
    @Override
    public boolean updateUser(User updatedUser) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        if (getUserById(updatedUser.getId()) == null) {
            LOGGER.log(Level.INFO, String.format("Update user failed: User ID %d not found", updatedUser.getId()));
            return false;
        }
        for (User user : users) {
            if (user.getId() == updatedUser.getId()) {
                users.set(users.indexOf(user), updatedUser);
            }
        }
        FileUtils.updateFile(FileUtils.FileType.USER, users, User::toString);
        LOGGER.log(Level.INFO, String.format("User updated successfully: ID %d", updatedUser.getId()));
        return true;
    }
    
    @Override
    public boolean removeUser(User userToRemove) throws RemoteException {
        if (userToRemove.getUserType().equals(UserType.ADMIN) || getUserById(userToRemove.getId()) == null) {
            LOGGER.log(Level.INFO, String.format("Remove user failed: User ID %d not found or is an admin", userToRemove.getId()));
            return false;
        }
        FileUtils.deleteFromFile(FileUtils.FileType.USER, userToRemove.getId());
        LOGGER.log(Level.INFO, String.format("User removed successfully: ID %d", userToRemove.getId()));
        return true;
    }
}
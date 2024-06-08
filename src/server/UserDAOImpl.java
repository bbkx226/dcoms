package server;

import models.User;
import models.UserType;
import remote.UserDAORemote;
import utils.FileUtils;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

// CRUD functions for user
public class UserDAOImpl extends UnicastRemoteObject implements UserDAORemote {
    public UserDAOImpl() throws RemoteException {
        super();
    }

    @Override
    public boolean addUser(String newFirstName, String newLastName, String newICNum, String newUsername, String newPassword) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        for (User user : users) {
            if (newUsername.equals(user.getUsername())) {
                return false;
            }
        }
        int newId = users.toArray().length + 1;
        UserType newUserType = UserType.CUSTOMER;
        User newUser = new User(newId, newUserType, newFirstName, newLastName, newICNum, newUsername, newPassword);
        FileUtils.appendToFile(FileUtils.FileType.USER, newUser, User::toString);
        return true;
    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        return FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
    }

    @Override
    public User getUserById(int userId) throws RemoteException {
        List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
        if (users.toArray()[userId - 1] != null) {
            return (User) users.toArray()[userId - 1];
        } else return null;
    }

    @Override
    public boolean updateUser(User updatedUser) throws RemoteException {
        if (getUserById(updatedUser.getId()) != null) {
            List<User> users = FileUtils.readFromFile(FileUtils.FileType.USER, User::fromString);
            users.set(updatedUser.getId() - 1, updatedUser);
            FileUtils.updateFile(FileUtils.FileType.USER, users, User::toString);
            return true;
        } else return false;
    }

    @Override
    public boolean removeUser(User userToRemove) throws RemoteException {
        if (userToRemove.getUserType().equals(UserType.ADMIN) || getUserById(userToRemove.getId()) == null) {
            return false;
        }
        FileUtils.deleteFromFile(FileUtils.FileType.USER, userToRemove.getId());
        return true;
    }
}

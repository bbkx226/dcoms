package remote;

import models.User;
import models.UserType;
import utils.FileUtils;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface UserDAORemote extends Remote {
    public boolean addUser(String newFirstName, String newLastName, String newICNum, String newUsername, String newPassword) throws RemoteException;
    public List<User> getAllUsers() throws RemoteException;
    public User getUserById(int userId) throws RemoteException;
    public boolean updateUser(User updatedUser) throws RemoteException;
    public boolean removeUser(User userToRemove) throws RemoteException;
}

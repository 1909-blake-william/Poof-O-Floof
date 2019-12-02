package dao;

import model.User;
import model.UserCreation;

public interface UserDao {
	User login(UserLogin loginCredentials);
	
	boolean createUser(UserCreation userCreation);
}

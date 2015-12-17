package cz.muni.fi.airport.dao;


import cz.muni.fi.airport.entity.User;
import java.util.Collection;
import java.util.List;

public interface UserDao {
	 public void create(User u);
	 public User findById(Long id);
	 public User findUserByUserName(String userName);
	 public  List<User> findAll();
}

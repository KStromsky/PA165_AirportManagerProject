package cz.muni.fi.airport.tests;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cz.muni.fi.airport.JpaTestContext;
import cz.muni.fi.airport.dao.UserDao;
import cz.muni.fi.airport.entity.User;

@ContextConfiguration(classes = {JpaTestContext.class})
@TestExecutionListeners(TransactionalTestExecutionListener.class)
@Transactional
public class UserDaoTest extends AbstractTestNGSpringContextTests {

	@Autowired
	private UserDao userdao;

	private User u1 ;
	private User u2;

	@BeforeMethod
	public void createUsers() {
		u1 = new User();
		u2 = new User();

		u1.setUserName("filip");
		u2.setUserName("jirka");
                
		userdao.create(u1);
		userdao.create(u2);
	}

	@Test
	public void findByEmail() {
		Assert.assertNotNull(userdao.findUserByUserName("filip"));
	}

	@Test
	public void findByNonExistentEmail() {
		Assert.assertNull(userdao.findUserByUserName("asdfasdfasd"));
	}

	/**
	 * Just helper method to create a valid user
	 * 
	 * @return
	 */
	public static User getSimpleUser() {
		User user = new User();
		user.setUserName("filip2");
		return user;
	}

	public static User getSimpleUser2() {
		User user = new User();
		user.setUserName("jirka2");
		return user;
	}

}
package com.onlineauction.user.domain.service.impl;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.rating.domain.entity.Rating;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.entity.UserType;
import com.onlineauction.user.domain.service.UserService;

public class UserServiceImplTest {

	private static final String INVALID_PASSWORD = "invalidPassword";
	private static final String TEST_EMAIL = "testEmail";
	private static final String TEST_PASSWORD = "testPassword";
	private static final String TEST_LAST_NAME = "testLastName";
	private static final String TEST_USER_NAME = "testUserName";
	private static final String TEST_FIRST_NAME = "testFirstName";

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());

	private final UserService userService = new UserServiceImpl();

	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}
	
	/**
	 * Test user registration.
	 */
	@Test
	public void testUserRegistration() {
		try {
			User user = createUser();
			userService.subscribeUser(user);
			
			//Subscription is done asynchronously. Need to wait before retrieving user.
			Thread.sleep(100);
			User createdUser = userService.getUserByUserName(TEST_USER_NAME);
			
			Assert.assertEquals("The user should have subscribed", user,
					createdUser);
		} catch (Exception e) {
			fail("No exceptions should be thrown");
		}
	}

	/**
	 * Test user deletion.
	 */
	@Test(expected=HgException.class)
	public void testUserDeletion() throws HgException{
			User user = createUser();
			userService.subscribeUser(user);
			
			User createdUser = userService.getUserByUserName(TEST_USER_NAME);
			
			Assert.assertEquals("The user should have subscribed", user,
					createdUser);
			
			userService.deleteUser(TEST_USER_NAME);
			
			user = userService.getUserByUserName(TEST_USER_NAME);
			Assert.assertNull("User should not exist", user);
	}
	
	@Test(expected=HgException.class)
	public void testGetUserByUserNameAndPasswordWithInvalidPassword() throws HgException{
		User user = createUser();
		userService.subscribeUser(user);
		
		userService.getUserByUserNameAndPassword(TEST_USER_NAME, INVALID_PASSWORD);
	}
	
	@Test
	public void testGetUserByUserNameAndPassword() {
		
		try {
			User user = createUser();
			userService.subscribeUser(user);
			
			Thread.sleep(100);
			User retrievedUser = userService.getUserByUserNameAndPassword(TEST_USER_NAME, TEST_PASSWORD);
			
			Assert.assertEquals("The user should have been retrieved", user,
					retrievedUser);
		} catch (Exception e) {
			fail("No exceptions should be thrown");
		}
	}
	
	@Test
	public void testRateUser() {
		try {
			User user = createUser();
			userService.subscribeUser(user);
			Thread.sleep(100);
			
			Date postingTime = new Date();
			Rating rating = new Rating("testDescription", 1, user.getUserName(), postingTime);
			Rating rating2 = new Rating("testDescription2", 1, user.getUserName(), postingTime);
			
			userService.rateUser(TEST_USER_NAME, rating);
			userService.rateUser(TEST_USER_NAME, rating2);
			
			User retrievedUser = userService.getUserByUserName(TEST_USER_NAME);
			
			
			Assert.assertEquals("The ratings cast should be same as expected", Arrays.asList(rating, rating2),
					retrievedUser.getRatings());
		} catch (Exception e) {
			fail("No exceptions should be thrown");
		}
	}
	

	private User createUser() {
		return new User(TEST_USER_NAME, UserType.BUYER, TEST_FIRST_NAME,
				TEST_LAST_NAME, TEST_PASSWORD, TEST_EMAIL);
	}
}

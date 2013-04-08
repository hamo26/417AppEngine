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
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.rating.domain.entity.Rating;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.entity.UserType;
import com.onlineauction.user.domain.service.UserService;

/**
 * Tests {@link UserServiceImpl}.
 * 
 * @author hamo
 *
 */
public class UserServiceImplTest {

	private static final Long AUCTION_ID = new Long(123);
	private static final double TEST_BID_PRICE = 98.0;
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
			
			userService.rateUser(TEST_USER_NAME, rating);
			
			User retrievedUser = userService.getUserByUserName(TEST_USER_NAME);
			
			
			Assert.assertEquals("The ratings cast should be same as expected", Arrays.asList(rating),
					retrievedUser.getRatings());
		} catch (Exception e) {
			fail("No exceptions should be thrown");
		}
	}
	
	@Test(expected = HgException.class)
	public void tetRateUserWithMultipleRatings() throws InterruptedException, HgException {
		User user = createUser();
		userService.subscribeUser(user);
		Thread.sleep(100);
		
		Date postingTime = new Date();
		Rating rating = new Rating("testDescription", 1, user.getUserName(), postingTime);
		Rating rating2 = new Rating("testDescription2", 1, "", postingTime);
		
		userService.rateUser(TEST_USER_NAME, rating);
		Thread.sleep(100);
		userService.rateUser(TEST_USER_NAME, rating2);
		
		User retrievedUser = userService.getUserByUserName(TEST_USER_NAME);
		
		
		Assert.assertEquals("The ratings cast should be same as expected", Arrays.asList(rating, rating2),
				retrievedUser.getRatings());
	}
	
	@Test
	public void testAddUserBid() {
		try {
			User user = createUser();
			userService.subscribeUser(user);
			Thread.sleep(100);
			
			Item testItem = new Item();
			Bid bid = new Bid(TEST_BID_PRICE, testItem, AUCTION_ID, TEST_USER_NAME);
			
			
			userService.addUserBid(TEST_USER_NAME, bid);
			Thread.sleep(100);
			
			User userAfterBid = userService.getUserByUserName(TEST_USER_NAME);
			
			
			Assert.assertTrue("the bids should only contain expected bid", userAfterBid.getBids().size() == 1);
			Assert.assertEquals("The bid should be the same as expecgted", bid, 
					userAfterBid.getBids().iterator().next());
			Assert.assertTrue("The user should contain one auction id", userAfterBid.getAuctionsIds().contains(AUCTION_ID));
		} catch (Exception e) {
			fail("No exceptions should be thrown");
		}
	}
	

	private User createUser() {
		return new User(TEST_USER_NAME, UserType.BUYER, TEST_FIRST_NAME,
				TEST_LAST_NAME, TEST_PASSWORD, TEST_EMAIL);
	}
}

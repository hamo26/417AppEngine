package com.onlineauction.item.domain.service.impl;

import static org.junit.Assert.fail;

import java.util.Date;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.user.domain.service.UserService;

/**
 * Tests the {@link AuctionServiceImpl} class.
 * 
 * @author hamo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuctionServiceImplTest {
	
	private static final Date START_TIME = new Date();

	private static final String TEST_USER_ID = "testUserId";

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private AuctionServiceImpl auctionService;
	
	private Item TEST_ITEM = new Item();

	private Date TEST_END_TIME = START_TIME;;
	
	
	@Before
	public void setUp() {
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	/**
	 * Test creating an auction.
	 * @throws InterruptedException 
	 */
	@Test
	public void testCreateAuction() throws InterruptedException {
		try {
			Auction expectedAuction = new Auction(START_TIME, TEST_END_TIME, TEST_ITEM, TEST_USER_ID);
			
			long createdAuctionId = auctionService.createAuction(TEST_USER_ID, TEST_ITEM, TEST_END_TIME);
			
			Thread.sleep(100);
			
			Auction createdAuction;
			createdAuction = auctionService.getAuctionById(createdAuctionId);
			assertEqualAuctions(expectedAuction, createdAuction);
		} catch (HgException e) {
			fail("Test should not fail");
		}
	}
	
	@Test
	public void testPlaceBidForAuction() throws Exception {
		Bid bid = new Bid();
		Mockito.doThrow(new RuntimeException()).doNothing().when(userService)
			.addUserBid(TEST_USER_ID, bid);
		
		long auctionId = auctionService.createAuction(TEST_USER_ID, TEST_ITEM, TEST_END_TIME);
		Thread.sleep(100);
		
		auctionService.placeBidForAuction(bid, auctionId);
		
		Thread.sleep(100);
		Auction auctionAfterBid = auctionService.getAuctionById(auctionId);
		
		Assert.assertTrue("There should only be one bid", 
					auctionAfterBid.getBidsPlaced().size() == 1);
		Assert.assertEquals("The bid placed should be same as expected",
				bid, auctionAfterBid.getBidsPlaced().iterator().next());
	}
	
	@Test(expected=HgException.class)
	public void testCleanupAuctions() throws InterruptedException, HgException {
		Auction auctionToBeDeleted = new Auction(START_TIME, TEST_END_TIME, TEST_ITEM, TEST_USER_ID);
		long auctionToBeDeletedId = auctionService.createAuction(TEST_USER_ID, TEST_ITEM, TEST_END_TIME);
		
		Thread.sleep(2000);
		
		Date currentDate = new Date();
		Assert.assertTrue("The auction should be expired", TEST_END_TIME.before(currentDate));
		try {
			Auction auction = auctionService.getAuctionById(auctionToBeDeletedId);
			assertEqualAuctions(auctionToBeDeleted, auction);
		} catch (HgException e) {
			fail("The test should not fail at this point. Clean up was not called.");
		}
		
		auctionService.cleanupAuctions();
		Thread.sleep(100);
		
		
		auctionService.getAuctionById(auctionToBeDeletedId);
	}
	
	private void assertEqualAuctions(Auction expectedAuction, Auction auction) {
		Assert.assertEquals("The auction sellers should be equal", expectedAuction.getSellerId(), 
								auction.getSellerId());
		Assert.assertEquals("The auction end times should be equal", expectedAuction.getEndTime(), 
				auction.getEndTime());
		Assert.assertEquals("The auction items should be equal", expectedAuction.getAuctionItem(), 
				auction.getAuctionItem());
		Assert.assertEquals("The auction bids should be equal", expectedAuction.getBidsPlaced(), 
				auction.getBidsPlaced());

	}
	
}

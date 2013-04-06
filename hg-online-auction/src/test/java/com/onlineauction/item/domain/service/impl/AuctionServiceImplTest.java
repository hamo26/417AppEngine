package com.onlineauction.item.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
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
	
	private static final String TEST_DESCRIPTION = "testDescription";

	private static final String TEST_NAME = "testName";

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
		
		assertTrue("There should only be one bid", 
					auctionAfterBid.getBidsPlaced().size() == 1);
		assertEquals("The bid placed should be same as expected",
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
	
	@Test
	public void testsearchForAuctionsByDescription() throws InterruptedException, ParseException {
		try {
			Item testItem = new Item(TEST_NAME, TEST_DESCRIPTION, 98.0);
			
			long auctionCreated = auctionService.createAuction(TEST_USER_ID, testItem, 
					new SimpleDateFormat("dd/MM/yyyy").parse("26/08/2013"));
			Thread.sleep(100);
			Auction expectedAuction = auctionService.getAuctionById(auctionCreated);
			Collection<Auction> auctions = auctionService.searchForAuctionsByDescription("te");
			
			assertTrue("There should only be one auction found", auctions.size() == 1);
			assertEquals("The auction returned should be the same as expected", expectedAuction,
					auctions.iterator().next());
		} catch (HgException e) {
			fail("The auction should have been found after creation");
		}
	}
	
	@Test
	public void testsearchForAuctionsByName() throws InterruptedException, ParseException {
		try {
			Item testItem = new Item(TEST_NAME, TEST_DESCRIPTION, 98.0);
			
			long auctionCreated = auctionService.createAuction(TEST_USER_ID, testItem, 
					new SimpleDateFormat("dd/MM/yyyy").parse("26/08/2013"));
			Thread.sleep(100);
			Auction expectedAuction = auctionService.getAuctionById(auctionCreated);
			Collection<Auction> auctions = auctionService.searchForAuctionsByName("te");
			
			assertTrue("There should only be one auction found", auctions.size() == 1);
			assertEquals("The auction returned should be the same as expected", expectedAuction,
					auctions.iterator().next());
		} catch (HgException e) {
			fail("The auction should have been found after creation");
		}
	}
	
	@Test
	public void testsearchForAuctionsByDescriptionWhenAuctionExpires() throws InterruptedException, ParseException {
			Item testItem = new Item(TEST_NAME, TEST_DESCRIPTION, 98.0);
			
			auctionService.createAuction(TEST_USER_ID, testItem, 
					new SimpleDateFormat("dd/MM/yyyy").parse("26/08/1900"));
			Thread.sleep(100);
			
			Collection<Auction> auctions = auctionService.searchForAuctionsByDescription("te");
			
			assertTrue("There should be no auctions found", auctions.isEmpty());
	}
	
	@Test(expected = HgException.class)
	public void testDeleteAuction() throws InterruptedException, HgException {
		long auctionToBeDeletedId = auctionService.createAuction(TEST_USER_ID, TEST_ITEM, TEST_END_TIME);
		
		Thread.sleep(100);
		
		try {
			auctionService.deleteAuction(auctionToBeDeletedId);
		} catch (HgException e) {
			fail("The deletion should not throw an exception at this point.");
		}
		
		Thread.sleep(100);
		
		auctionService.getAuctionById(auctionToBeDeletedId);
	}
	
	@Test
	public void testIsAuctionExpired() throws HgException {
		long auctionId = auctionService.createAuction(TEST_USER_ID, TEST_ITEM, TEST_END_TIME);
		assertTrue("The auction should be expired", auctionService.isAuctionExpired(auctionId));
		
	}
	
	@Test
	public void testGetHighestBidForAuction() {
		try {
			long auctionId = auctionService.createAuction(TEST_USER_ID, TEST_ITEM, TEST_END_TIME);
			Bid bid1 = new Bid(10.0,TEST_ITEM,auctionId,TEST_USER_ID);
			Bid bid2 = new Bid(30.0, TEST_ITEM,auctionId,TEST_USER_ID);
			Bid highestBid = new Bid(250.0, TEST_ITEM,auctionId,TEST_USER_ID);
			auctionService.placeBidForAuction(bid1, auctionId);
			auctionService.placeBidForAuction(highestBid, auctionId);
			auctionService.placeBidForAuction(bid2, auctionId);
			
			Bid highestBidForAuction = auctionService.getHighestBidForAuction(auctionId);
			
			assertEquals("the highest bid for the auction should be 30", highestBid.getBidPrice(), highestBidForAuction.getBidPrice());
		} catch (HgException e) {
			fail("The test should not throw an exception");
		}
	}
	
	@Test
	public void testGetHighestBidForAuctionWithNoBids() {
		try {
			long auctionId = auctionService.createAuction(TEST_USER_ID, TEST_ITEM, TEST_END_TIME);
			
			Bid highestBidForAuction = auctionService.getHighestBidForAuction(auctionId);
			
			assertNull("the highest bid should be null", highestBidForAuction);
		} catch (HgException e) {
			fail("The test should not throw an exception");
		}
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

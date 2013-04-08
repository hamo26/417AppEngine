package com.onlineauction.item.domain.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.item.domain.service.AuctionSearchService;
import com.onlineauction.user.domain.service.UserService;

/**
 * Tests {@link AuctionSearchServiceImpl}.
 * 
 * @author hamo
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class AuctionSearchServiceImplTest {

	private static final String TEST_DESCRIPTION = "testDescription";

	private static final String TEST_NAME = "testName";

	private static final String TEST_USER_ID = "testUserId";

	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig());
	
	@Mock
	private UserService userService;
	
	@InjectMocks
	private AuctionServiceImpl auctionService;

	private AuctionSearchService auctionSearchService;
	
	
	@Before
	public void setUp() {
		auctionSearchService = new AuctionSearchServiceImpl();
		helper.setUp();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	
	@Test
	public void testsearchForAuctionsByDescription() throws InterruptedException, ParseException {
		try {
			Item testItem = new Item(TEST_NAME, TEST_DESCRIPTION, 98.0);
			
			long auctionCreated = auctionService.createAuction(TEST_USER_ID, testItem, 
					new SimpleDateFormat("dd/MM/yyyy").parse("26/08/2013"));
			Thread.sleep(100);
			Auction expectedAuction = auctionService.getAuctionById(auctionCreated);
			Collection<Auction> auctions = auctionSearchService.searchForNonExpiredAuctionsByDescription("te");
			
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
			Collection<Auction> auctions = auctionSearchService.searchForNonExpiredAuctionsByName("te");
			
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
			
			Collection<Auction> auctions = auctionSearchService.searchForNonExpiredAuctionsByDescription("te");
			
			assertTrue("There should be no auctions found", auctions.isEmpty());
	}
}

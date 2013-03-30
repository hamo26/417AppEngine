package com.onlineauction.objectify;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.rating.domain.entity.Rating;
import com.onlineauction.user.domain.entity.User;

public class HgDataService {

		static {
			//We need to register all our domain objects with objectify.
			factory().register(User.class);
			factory().register(Item.class);
			factory().register(Bid.class);
			factory().register(Auction.class);
			factory().register(Rating.class);
		}
		
		public static Objectify objectify() {
			return ObjectifyService.ofy();
		}
		
		public static ObjectifyFactory factory() {
			return ObjectifyService.factory();
		}
}

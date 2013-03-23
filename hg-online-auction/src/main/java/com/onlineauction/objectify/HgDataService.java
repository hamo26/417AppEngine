package com.onlineauction.objectify;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.onlineauction.user.domain.entity.User;

public class HgDataService {

		static {
			//We need to register all our domain objects with objectify.
			factory().register(User.class);
		}
		
		public static Objectify objectify() {
			return ObjectifyService.ofy();
		}
		
		public static ObjectifyFactory factory() {
			return ObjectifyService.factory();
		}
}

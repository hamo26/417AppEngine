package com.onlineauction.user.name.domain.service.impl;

import com.onlineauction.objectify.HgDataService;
import com.onlineauction.user.name.domain.entity.UserName;
import com.onlineauction.user.name.domain.service.UserNameService;

/**
 * Implementation of {@link UserNameService}.
 * 
 * @author hamo
 *
 */
public class UserNameServiceImpl implements UserNameService {

	@Override
	public void addUserName(String userName) {
		HgDataService.objectify()
				     .save()
				     .entity(new UserName(userName))
				     .now();
	}

	@Override
	public Boolean isUserNameValidForRegistration(String userName) {
		UserName userNameReturned = HgDataService.objectify()
					 .load()
					 .type(UserName.class)
					 .filter("userName ==", userName)
					 .first()
					 .get();
		
		return userNameReturned == null;
	}

	
}

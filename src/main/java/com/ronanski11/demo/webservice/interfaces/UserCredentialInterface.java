package com.ronanski11.demo.webservice.interfaces;

import com.ronanski11.demo.webservice.model.UserCredentials;
import com.ronanski11.demo.webservice.model.UserInfo;

public interface UserCredentialInterface {
	
	UserCredentials findAllById(String username);
	
	UserInfo findAllInfoById(String username);
	
	UserCredentials save(UserCredentials usrCred);
}

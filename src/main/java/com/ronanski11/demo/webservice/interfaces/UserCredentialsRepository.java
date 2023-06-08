package com.ronanski11.demo.webservice.interfaces;

import org.springframework.data.repository.CrudRepository;

import com.ronanski11.demo.webservice.model.UserCredentials;
import com.ronanski11.demo.webservice.model.UserInfo;

public interface UserCredentialsRepository extends CrudRepository<UserCredentials, String>{

	void save(UserInfo userInfo);
	
}

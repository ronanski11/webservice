package com.ronanski11.demo.webservice.interfaces.implementations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ronanski11.demo.webservice.interfaces.UserCredentialInterface;
import com.ronanski11.demo.webservice.interfaces.UserCredentialsRepository;
import com.ronanski11.demo.webservice.interfaces.UserInfoRepository;
import com.ronanski11.demo.webservice.model.UserCredentials;
import com.ronanski11.demo.webservice.model.UserInfo;

@Service
public class UserCredentialsImplementation implements UserCredentialInterface{
	
	@Autowired
	UserCredentialsRepository usrCredRepo;
	
	@Autowired
	UserInfoRepository userInfoRepo;
	
	
	@Override
	public UserCredentials findAllById(String username) {
		Optional<UserCredentials> optionalUserCredentials  = usrCredRepo.findById(username);
		if (optionalUserCredentials.isPresent()) {
			return optionalUserCredentials.get();
		}
		else {
			return null;
		}
	}
	
	@Override
	public UserInfo findAllInfoById(String username) {
		Optional<UserInfo> optionalUserInfo  = userInfoRepo.findById(username);
		if (optionalUserInfo.isPresent()) {
			return optionalUserInfo.get();
		}
		else {
			return null;
		}
	}
	@Override
	public UserCredentials save(UserCredentials usrCred) {
		usrCredRepo.save(usrCred);
		return usrCred;
	}
	
}

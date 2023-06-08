package com.ronanski11.demo.webservice.interfaces;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.ronanski11.demo.webservice.model.UserInfo;

public interface UserInfoRepository extends CrudRepository<UserInfo, String>{

	Optional<UserInfo> findById(String username);

}

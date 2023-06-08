package com.ronanski11.demo.webservice.interfaces;

public interface EmailSenderService {
	void sendEmail(String to, String subject, String content);
}

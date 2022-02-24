package com.exeter.sender;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendController {

	@Autowired
	ArtemisPublisher pub;
	
	@GetMapping("/send/{message}") //1. When a message is received by the API send endpoint in your Sender service
	public String sendMessage(@PathVariable("message") String message) {
		pub.sendMessage(message);
		return "Message sent!";
	}
	@GetMapping("/sendBytes/{message}")
	public String sendBytesMessage(@PathVariable("message") String message) {
	    pub.sendBytesMessage(message);
	    return "Message sent!";
	}
}

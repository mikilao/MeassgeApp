package com.exeter.sender;

import javax.jms.BytesMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtemisPublisher {

	@Autowired
	JmsTemplate template;
	
	@Value("${jms.queue.name}")
	String queueName;
	
	public void sendMessage(String message) {
		template.convertAndSend(queueName, message);
	}
	public void sendBytesMessage(String message) {
	    template.send(queueName, s -> {
	        BytesMessage msg = s.createBytesMessage();
	        msg.setJMSType("bytes"); //Sets up the JMS listeners
	        msg.writeBytes(message.getBytes());
	        return msg; //you are creating a new variable called msg that is a type of BytesMessage. 
	    });
	}
	        
	        
	        
	        
	       
	    
	}



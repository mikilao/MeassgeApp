package com.exeter.sender;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtemisPublisher {

	@Autowired
	JmsTemplate template;
	
	@Value("${jms.queue.name}")
	String queueName;
	
	@Value("${jms.replyqueue.name}")
	String replyQueueName; // property to contain that reply queue name.
	
	public void sendMessage(String message) { 
	    template.send(queueName, s -> {
	        Queue replyQueue = s.createQueue(replyQueueName); // Get the reply queue object
	        TextMessage msg = s.createTextMessage(message);
	        msg.setJMSType("text");
	        msg.setJMSReplyTo(replyQueue); // Send the reply queue object as the ReplyTo header
	        return msg;
	    });
	}
	public void sendBytesMessage(String message) {
	    template.send(queueName, s -> {
	        BytesMessage msg = s.createBytesMessage();
	        msg.setJMSType("bytes"); //Sets up the JMS listeners
	        msg.writeBytes(message.getBytes());
	        return msg; //you are creating a new variable called msg that is a type of BytesMessage. 
	    });
	}
	@JmsListener(destination = "${jms.replyqueue.name}")
	public void onReplyQueueMessage(Message message) {
	    try {
	        if (message instanceof TextMessage) {// Now when the onReplyQueueuMesssage method receives a reply, it will post to the topicTextMessages Topic
	            TextMessage textMessage = (TextMessage) message;
	            System.out.println("Reply Received: " + textMessage.getText());
	            template.setPubSubDomain(true);
	            template.convertAndSend("topicTextMessages", "Publishing: " + textMessage.getText());
	            template.setPubSubDomain(true);
	        }
	    } catch (JMSException e) {
	        System.out.println("Error receiving Reply:");
	        e.printStackTrace();
	    }
	}
	        
	        
	        
	       
	    
	}



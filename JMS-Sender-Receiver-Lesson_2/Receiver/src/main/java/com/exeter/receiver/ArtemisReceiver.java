package com.exeter.receiver;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ArtemisReceiver {

	@Autowired
	JmsTemplate template;
	
    @JmsListener(destination = "${jms.queue.name}")
    public void receiveMessage(Message message) {
        try {
            if (message instanceof BytesMessage) {
                BytesMessage bytesMessage = (BytesMessage) message;
                byte[] byteArr = new byte[(int)bytesMessage.getBodyLength()];
                bytesMessage.readBytes(byteArr);
                String stringMessage = new String(byteArr);
                System.out.println("Message Received: " + stringMessage);
            } else if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                System.out.println("Message Received: " + textMessage.getText());
            }
        } catch (JMSException e) {
            System.out.println("Error receiving Message:");
            e.printStackTrace();
        }
    }//@JmsListener(destination = "${jms.queue.name}", selector = "JMSType='bytes'")
    public void receiveBytesMessages(BytesMessage bytesMessage) {
        try {
            byte[] byteArr = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(byteArr);
            String stringMessage = new String(byteArr);
            System.out.println("Message Received: " + stringMessage);
        } catch (JMSException e) {
            System.out.println("Error receiving Message:");
            e.printStackTrace();
        }
    }
    @JmsListener(destination = "${jms.queue.name}", selector = "JMSType='bytes'")
    public void receiveBytesMessage(BytesMessage bytesMessage) {
        try {
            byte[] byteArr = new byte[(int) bytesMessage.getBodyLength()];
            bytesMessage.readBytes(byteArr);
            String stringMessage = new String(byteArr);
            System.out.println("Message Received: " + stringMessage);
        } catch (JMSException e) {
            System.out.println("Error receiving Message:");
            e.printStackTrace();
        }
    }
    @JmsListener(destination = "${jms.queue.name}", selector = "JMSType='text'")
    public void receiveTextMessage(TextMessage message) { //his method will receive the TextMessage from the "Sender" service and check for a value on the getJMSReplyTo method
        try {
            System.out.println("Message Received: " + message.getText());
            if (message.getJMSReplyTo() != null) {
                template.convertAndSend(message.getJMSReplyTo(), "Message Acknowledged");
            }
        } catch (JMSException e) {
            System.out.println("Error receiving Message:");
            e.printStackTrace();
        }
    }
    @JmsListener(destination = "topicTextMessages", containerFactory = "myFactory")
    public void receiveTopicTextMessage(TextMessage message) {
        try {
            System.out.println("Topic Message Received: " + message.getText());
            if (message.getJMSReplyTo() != null) {
                template.convertAndSend(message.getJMSReplyTo(), "Message Acknowledged");
            }
        } catch (JMSException e) {
            System.out.println("Error receiving Topic Message: ");
            e.printStackTrace();
        }
    }
}
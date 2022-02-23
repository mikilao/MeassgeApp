package com.exeter.receiver;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.TextMessage;

import org.apache.activemq.artemis.api.core.Message;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ArtemisReceiver {

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
}
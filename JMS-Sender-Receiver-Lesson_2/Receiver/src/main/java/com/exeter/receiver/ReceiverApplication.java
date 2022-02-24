package com.exeter.receiver;

import javax.jms.ConnectionFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@SpringBootApplication
public class ReceiverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReceiverApplication.class, args);
	}
	
	@Bean
	public MessageConverter productOrderMessageConverter() {
	    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
	    converter.setTargetType(MessageType.TEXT);
	    converter.setTypeIdPropertyName("_type");
	    return converter;
	}
	//3. In order for this method to listen for a Topic rather than a Queue message, the myFactory container was created in the ReceiverApplication class and specified as the container factory in the JmsListener.
	@Bean
	public JmsListenerContainerFactory<?> myFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    configurer.configure(factory, connectionFactory);
	    factory.setPubSubDomain(true);
	    return factory;
	}
	
	//istens to the ProductOrders Topic and converts JSON to Java objects.
	@Bean
	public JmsListenerContainerFactory<?> ProductOrdersFactory(ConnectionFactory connectionFactory, DefaultJmsListenerContainerFactoryConfigurer configurer) {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    configurer.configure(factory, connectionFactory);
	    factory.setPubSubDomain(true);
	    factory.setMessageConverter(productOrderMessageConverter());
	    return factory;
	}
}
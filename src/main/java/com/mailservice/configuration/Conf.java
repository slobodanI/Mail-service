package com.mailservice.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Conf {
	
	  public static final String QUEUE = "mail-queue";

	    @Bean
	    public Queue queue(){
	        return new Queue(QUEUE, false);
	    }

	    @Bean
	    public ConnectionFactory connectionFactory() {
	        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
	        connectionFactory.setUsername("zpbtlzju");
	        connectionFactory.setPassword("JzrwM8K1auo_bde4VsNpo6HjMWAJwPZz");
	        connectionFactory.setVirtualHost("zpbtlzju");
	        connectionFactory.setHost("bee.rmq.cloudamqp.com");
//	        connectionFactory.setPort();
	        return connectionFactory;
	    }
	
}

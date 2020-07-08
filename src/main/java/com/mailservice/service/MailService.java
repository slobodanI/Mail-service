package com.mailservice.service;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mailservice.configuration.Conf;
import com.mailservice.dto.MailDTO;

@Service
public class MailService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private Environment env;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@RabbitListener(queues = Conf.QUEUE)
    public void sendMail(String mail) {
		logger.info("Sending mail...");
        try {
            MailDTO mailDTO = objectMapper.readValue(mail, MailDTO.class);
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(Objects.requireNonNull(env.getProperty("spring.mail.username")));
            message.setTo(mailDTO.getEmail());
            message.setSubject(mailDTO.getSubject());
            message.setText(mailDTO.getMessage());
            javaMailSender.send(message);
        } catch (JsonProcessingException exception) {
        	logger.error("Error while sending email. Coult not read data.");
        } catch(Exception e) {
            e.printStackTrace();
        }
        logger.info("Mail sent successfully.");
    }
	
}

package com.task.config;






import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.spring.ActiveMQConnectionFactory;

import org.apache.activemq.store.kahadb.KahaDBPersistenceAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import java.io.File;
import java.util.Arrays;

@Configuration
@EnableJms
@ComponentScan("com.task")
public class JmsConfig {
    private static String BROKER_URL = "tcp://docker.for.win.localhost:61616";
    private static String BROKER_USERNAME = "admin";
    private static String BROKER_PASSWORD = "admin";
    private static String DESTINATION_NAME = "eCare";

    /**
     *in comment below variant for embedded broker
     * without persistent
     */

//    @Bean
//    public BrokerService broker() throws Exception {
//        BrokerService broker = new BrokerService();
//        broker.setPersistent(false);
//        broker.setUseJmx(true);
//        broker.addConnector(BROKER_URL);
//
//        return broker;
//    }

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(BROKER_URL);
        connectionFactory.setPassword(BROKER_USERNAME);
        connectionFactory.setUserName(BROKER_PASSWORD);
        connectionFactory.setTrustedPackages(Arrays.asList("com.task.dto"));
        connectionFactory.setTrustAllPackages(true);
        return connectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate() {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setDefaultDestinationName(DESTINATION_NAME);
        template.setMessageConverter(jacksonJmsMessageConverter());
        return template;
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }





}

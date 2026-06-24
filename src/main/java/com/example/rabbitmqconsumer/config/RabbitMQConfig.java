package com.example.rabbitmqconsumer.config;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
        
        // Configuramos el mapeador de clases
        DefaultClassMapper classMapper = new DefaultClassMapper();
        
        // 1. Decimos que confíe en los paquetes
        classMapper.setTrustedPackages("com.example.rabbitmqproductor.dto", "com.example.rabbitmqconsumer.dto");
        
        // 2. Mapeamos el tipo del Productor al tipo del Consumidor
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.example.rabbitmqproductor.dto.AlertaRequest", AlertaRequest.class);
        classMapper.setIdClassMapping(idClassMapping);
        
        converter.setClassMapper(classMapper);
        return converter;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory, 
            Jackson2JsonMessageConverter jsonMessageConverter) { // Inyectamos el conversor que ya creamos
        
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        
        // AQUÍ ESTÁ EL ESLABÓN PERDIDO:
        // Le decimos a la fábrica que use TU conversor en lugar del de por defecto
        factory.setMessageConverter(jsonMessageConverter);
        
        return factory;
    }
}
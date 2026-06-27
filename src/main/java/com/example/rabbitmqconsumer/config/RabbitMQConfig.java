package com.example.rabbitmqconsumer.config;

import com.example.rabbitmqconsumer.dto.AlertaRequest;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
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

    public static final String EXCHANGE_NAME = "alerta-exchange";
    public static final String QUEUE_BD = "Cola-Alerta-BD";

    // 1. Declaración de Exchange y Cola
    @Bean
    public FanoutExchange alertaExchange() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue colaAlertaBD() {
        return new Queue(QUEUE_BD);
    }

    // 2. Binding: Conectamos la cola al exchange
    @Bean
    public Binding bindingBD(FanoutExchange alertaExchange, Queue colaAlertaBD) {
        return BindingBuilder.bind(colaAlertaBD).to(alertaExchange);
    }

    // 3. Conversor JSON con el mapeador de clases
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("com.example.rabbitmqproductor.dto",
                "com.example.rabbitmqconsumer.dto");

        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.example.rabbitmqproductor.dto.AlertaRequest", AlertaRequest.class);
        classMapper.setIdClassMapping(idClassMapping);

        converter.setClassMapper(classMapper);
        return converter;
    }

    // 4. Fábrica de contenedores que usa el conversor
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jsonMessageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonMessageConverter);

        return factory;
    }
}

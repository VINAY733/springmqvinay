package com.springmq.springmqvinay;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class MessageConsume {

        @Bean
        public Consumer<Employee> consumeMessage() {
            return message -> {
                System.out.println("🐰 Received message from RabbitMQ: " + message);
            };

    }

}

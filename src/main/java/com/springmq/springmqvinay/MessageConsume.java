package com.springmq.springmqvinay;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.function.Consumer;

@Configuration
public class MessageConsume {

    private final StreamBridge streamBridge;


    public MessageConsume(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    @Bean
    @Profile({"rabbit", "both"})
    public Consumer<Employee> consumeRabbit() {
        return message -> {
            System.out.println("🐰 Received message from RabbitMQ: " + message);
        };
    }

    @Profile({"kafka", "both"})
    @Bean
    public Consumer<Employee> consumeKafka() {
        return message -> {
            System.out.println("🐰 Received message from kafka: " + message);
            streamBridge.send("publishKafka-out-0", message);
            System.out.println("🐰 messsage sent to output");

        };
    }

}
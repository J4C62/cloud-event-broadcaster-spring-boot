package com.github.j4c62.infrastructure.rabbit.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

  @Bean
  public Queue myQueue() {
    return new Queue("myQueue", true);
  }
}

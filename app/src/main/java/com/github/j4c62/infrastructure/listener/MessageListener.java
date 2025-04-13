package com.github.j4c62.infrastructure.listener;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.j4c62.application.usecase.Poster;
import com.github.j4c62.infrastructure.delivery.dto.CloudEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageListener {
  private final Poster poster;
  private final ObjectMapper mapper;

  @RabbitListener(queues = "myQueue")
  public void handleMessage(String event) {
    try {
      var cloudEvent = mapper.readValue(event, new TypeReference<CloudEvent>() {});
      poster.post(cloudEvent);
    } catch (Exception ex) {
      System.out.println(ex);
    }
  }
}

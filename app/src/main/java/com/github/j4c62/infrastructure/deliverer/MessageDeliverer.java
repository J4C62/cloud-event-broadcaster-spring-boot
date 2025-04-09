package com.github.j4c62.infrastructure.deliverer;

import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Deliverer;
import com.github.j4c62.delivery.Diffusible;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageDeliverer implements Deliverer {
  private final RabbitTemplate rabbitTemplate;

  @Override
  public void deliver(Diffusible diffusible) {
    rabbitTemplate.convertAndSend("myQueue", diffusible);
  }

  @Override
  public Channel getChannel() {
    return Channel.EVENT_BRIDGE;
  }
}

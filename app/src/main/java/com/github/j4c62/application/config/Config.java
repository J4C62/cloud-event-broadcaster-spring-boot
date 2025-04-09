package com.github.j4c62.application.config;

import com.github.j4c62.application.usecase.Poster;
import com.github.j4c62.broadcaster.Broadcaster;
import com.github.j4c62.composer.DiffusibleComposer;
import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.dto.CloudEvent;
import com.github.j4c62.infrastructure.dto.Email;
import com.github.j4c62.selector.DelivererSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
public class Config {

  @Bean
  public Poster poster(final DelivererSelector delivererSelector, final DiffusibleComposer diffusibleComposer) {
    return message ->
        Broadcaster.spec(delivererSelector, diffusibleComposer)
            .onError(System.out::println)
            .onDelivery(System.out::println)
            .filter(x -> !x.getChannel().equals(Channel.EVENT_BRIDGE))
            .when(((diffusible, broadcaster) -> true)).execute(message);
  }

  @Bean
  public List<Diffusible> diffusibles() {
    return List.of(new Email("This is a message"), new CloudEvent("test", "test", "test", "test", "test", "test", Map.of()));
  }

  @Bean
  public DiffusibleComposer diffusibleComposer(List<Diffusible> diffusibles) {
    return () -> diffusibles;
  }
}


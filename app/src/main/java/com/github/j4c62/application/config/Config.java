package com.github.j4c62.application.config;

import com.github.j4c62.application.usecase.Poster;
import com.github.j4c62.broadcaster.Broadcaster;
import com.github.j4c62.composer.DiffusibleComposer;
import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.dto.Email;
import com.github.j4c62.selector.DelivererSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
  public List<Diffusible> diffusibles(ApplicationProperties applicationProperties) {
    return List.of(new Email("This is a message"));
  }

  @Bean
  public DiffusibleComposer diffusibleComposer(List<Diffusible> diffusibles) {
    return () -> diffusibles;
  }

  @Bean
  public List<ApplicationProperties.ChannelConfig> channels(ApplicationProperties applicationProperties) {
    System.out.println(applicationProperties);
    return applicationProperties.getChannels();
  }
}


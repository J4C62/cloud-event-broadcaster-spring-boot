package com.github.j4c62.application.config;

import com.github.j4c62.application.usecase.Poster;
import com.github.j4c62.broadcaster.Broadcaster;
import com.github.j4c62.composer.DiffusibleComposer;
import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.delivery.DiffusibleFactory;
import com.github.j4c62.infrastructure.delivery.dto.CloudEvent;
import com.github.j4c62.selector.DelivererSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainConfig {

  private static void getBroadcast(DelivererSelector delivererSelector, Diffusible message, TemplateConfig.NotificationTemplateEngine engine, ApplicationProperties applicationProperties) {
    DiffusibleComposer diffusibleComposer = () -> DiffusibleFactory.createDiffusibles((CloudEvent) message, applicationProperties, engine);
    Broadcaster.spec(delivererSelector, diffusibleComposer)
        .onError(System.out::println)
        .onDelivery(System.out::println)
        .filter(x -> !x.getChannel().equals(Channel.EVENT_BRIDGE))
        .when(((diffusible, broadcaster) -> true)).execute(message);

  }


  @Bean
  public Poster poster(
      final DelivererSelector delivererSelector, final TemplateConfig.NotificationTemplateEngine engine, ApplicationProperties applicationProperties) {
    return message -> getBroadcast(delivererSelector, message, engine, applicationProperties);

  }

  @Bean
  public List<ApplicationProperties.ChannelConfig> channels(
      ApplicationProperties applicationProperties) {
    return applicationProperties.getChannels();
  }


}

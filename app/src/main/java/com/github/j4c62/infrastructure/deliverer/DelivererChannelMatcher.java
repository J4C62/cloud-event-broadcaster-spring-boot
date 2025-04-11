package com.github.j4c62.infrastructure.deliverer;

import com.github.j4c62.application.config.ApplicationProperties;
import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Deliverer;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.dto.CloudEvent;
import com.github.j4c62.selector.DelivererSelector;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.function.Predicate;

@Repository
@RequiredArgsConstructor
@SuppressWarnings("unused")
public class DelivererChannelMatcher implements DelivererSelector {
  private final List<Deliverer> deliverers;
  private final List<ApplicationProperties.ChannelConfig> channels;

  @Override
  public List<Deliverer> findDelivers(Diffusible diffusible) {
    CloudEvent event = (CloudEvent) diffusible;

    return deliverers.stream()
        .filter(deliverer -> isDelivererMatchingChannel(deliverer, event))
        .toList();
  }

  private boolean isDelivererMatchingChannel(Deliverer deliverer, CloudEvent event) {
    return channels.stream()
        .filter(buildChannelConfigPredicate(event))
        .flatMap(channelConfig -> channelConfig.getChannel().stream())
        .anyMatch(channel -> isChannelMatching(deliverer, channel));
  }

  private boolean isChannelMatching(Deliverer deliverer, Channel channel) {
    return channel.equals(deliverer.getChannel());
  }

  private static Predicate<ApplicationProperties.ChannelConfig> buildChannelConfigPredicate(CloudEvent event) {
    return channelConfig -> channelConfig.getSource().equals(event.source()) && channelConfig.getType().equals(event.type());
  }
}

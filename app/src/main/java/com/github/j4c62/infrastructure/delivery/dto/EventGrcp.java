package com.github.j4c62.infrastructure.delivery.dto;

import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.grcp.EventRequest;

public record EventGrcp(EventRequest eventRequest) implements Diffusible {
  @Override
  public Channel getChannel() {
    return Channel.GRCP;
  }
}

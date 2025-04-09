package com.github.j4c62.infrastructure.dto;

import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Diffusible;

public record Email(String message) implements Diffusible {
  @Override
  public Channel getChannel() {
    return Channel.EMAIL;
  }
}

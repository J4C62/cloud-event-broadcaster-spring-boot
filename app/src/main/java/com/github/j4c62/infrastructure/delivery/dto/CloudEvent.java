package com.github.j4c62.infrastructure.delivery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Diffusible;
import java.util.Map;

public record CloudEvent(
    @JsonProperty("id") String id,
    @JsonProperty("specversion") String specVersion,
    @JsonProperty("source") String source,
    @JsonProperty("type") String type,
    @JsonProperty("time") String time,
    @JsonProperty("data") String message,
    @JsonProperty("extensions") Map<String, Object> extensions)
    implements Diffusible {

  @Override
  public Channel getChannel() {
    return Channel.EVENT_BRIDGE;
  }
}

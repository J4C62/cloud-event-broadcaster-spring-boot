package com.github.j4c62.infrastructure.delivery.dto;

import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Diffusible;

import java.util.List;

public record Email(
    String from,
    String to,
    String subject,
    String body,
    boolean isHtml,
    List<String> cc,
    List<String> bcc,
    List<EmailAttachment> attachments)
    implements Diffusible {
  @Override
  public Channel getChannel() {
    return Channel.EMAIL;
  }

  public record EmailAttachment(String filename, byte[] content, String contentType) {
  }
}

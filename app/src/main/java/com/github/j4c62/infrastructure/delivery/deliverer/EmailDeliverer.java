package com.github.j4c62.infrastructure.delivery.deliverer;

import com.github.j4c62.data.Channel;
import com.github.j4c62.delivery.Deliverer;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.delivery.dto.Email;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailDeliverer implements Deliverer {
  private final JavaMailSender mailSender;

  @SneakyThrows
  @Override
  public void deliver(Diffusible diffusible) {
    Email email = (Email) diffusible;
    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
    helper.setTo(email.to());
    helper.setSubject(email.subject());
    helper.setText(email.body(), true);
    helper.setFrom(email.from());

    mailSender.send(message);
    System.out.println("Email send");
  }

  @Override
  public Channel getChannel() {
    return Channel.EMAIL;
  }
}

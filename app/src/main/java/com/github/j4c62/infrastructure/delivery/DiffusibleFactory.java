package com.github.j4c62.infrastructure.delivery;

import com.github.j4c62.application.config.ApplicationProperties;
import com.github.j4c62.application.config.TemplateConfig;
import com.github.j4c62.delivery.Diffusible;
import com.github.j4c62.infrastructure.delivery.dto.CloudEvent;
import com.github.j4c62.infrastructure.delivery.dto.Email;
import com.github.j4c62.infrastructure.delivery.dto.EventGrcp;
import com.github.j4c62.infrastructure.grcp.EventRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

@Component
public class DiffusibleFactory {

  public static List<Diffusible> createDiffusibles(
      CloudEvent cloudEvent,
      ApplicationProperties applicationProperties,
      TemplateConfig.NotificationTemplateEngine engine) {
    return List.of(buildEmail(cloudEvent, applicationProperties, engine), buildGrcp(cloudEvent));
  }

  private static Diffusible buildEmail(
      CloudEvent cloudEvent,
      ApplicationProperties applicationProperties,
      TemplateConfig.NotificationTemplateEngine engine) {
    String body = resolveTemplateBody(cloudEvent, applicationProperties, engine);

    return new Email(
        "form@from.com",
        "usuario@dominio.com",
        "Hola desde Spring",
        body,
        true,
        List.of("copiado@dominio.com"),
        List.of(),
        List.of());
  }

  private static Diffusible buildGrcp(Diffusible diffusible) {
    CloudEvent cloudEvent = (CloudEvent) diffusible;
    EventRequest eventRequest =
        EventRequest.newBuilder()
            .setId(cloudEvent.id())
            .setSource(cloudEvent.source())
            .setType(cloudEvent.type())
            .setTime(cloudEvent.time())
            .setData(cloudEvent.message())
            .build();

    return new EventGrcp(eventRequest);
  }

  private static String resolveTemplateBody(
      CloudEvent event,
      ApplicationProperties props,
      TemplateConfig.NotificationTemplateEngine engine) {
    return props.getTemplates().stream()
        .filter(t -> t.getSource().equals(event.source()))
        .filter(t -> t.getType().equals(event.type()))
        .findFirst()
        .map(templateConfig -> processTemplate(templateConfig.getTemplate(), event, engine))
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "No template found for event: " + event.source() + " / " + event.type()));
  }

  @SneakyThrows
  private static String processTemplate(
      String template, CloudEvent event, TemplateConfig.NotificationTemplateEngine engine) {
    Map<String, Object> variables = new HashMap<>(event.extensions());
    return engine.process(template, variables, Locale.getDefault());
  }
}

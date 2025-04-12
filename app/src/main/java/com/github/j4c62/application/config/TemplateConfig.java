package com.github.j4c62.application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Configuration
public class TemplateConfig {


  @Bean
  public NotificationTemplateEngine thymeleafEngine() {
    StringTemplateResolver templateResolver = new StringTemplateResolver();
    templateResolver.setCacheable(true);
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);

    return (template, variables, locale) ->
        new SpringTemplateEngine().process(template, new Context(locale, variables));
  }


  public interface NotificationTemplateEngine {
    String process(String template, Map<String, Object> variables, Locale locale)
        throws IOException;
  }
}

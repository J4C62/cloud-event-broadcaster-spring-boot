package com.github.j4c62.application.config;

import com.github.j4c62.data.Channel;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
@Data
@ConfigurationProperties("application")
public class ApplicationProperties {
  private List<ChannelConfig> channels;
  private List<TemplatesConfig> templates;

  @Data
  public static class ChannelConfig {
    private String source;
    private String type;
    private List<Channel> channel;
  }

  @Data
  public static class TemplatesConfig {
    private String source;
    private String type;
    private String template;
  }
}

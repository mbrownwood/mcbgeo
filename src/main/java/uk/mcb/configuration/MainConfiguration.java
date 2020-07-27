package uk.mcb.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@Slf4j
public class MainConfiguration {

  @Bean
  public RestTemplate restTemplate() {
    log.debug("Attempting to create bean RestTemplate");
    return new RestTemplate();
  }
}

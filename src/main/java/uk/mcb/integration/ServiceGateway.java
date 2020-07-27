package uk.mcb.integration;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceGateway {

  private static final String GET_LONDON_USERS =
      "http://bpdts-test-app.herokuapp.com/city/London/users";
  private static final String GET_USERS = "http://bpdts-test-app.herokuapp.com/users";

  private final RestTemplate restTemplate;

  public List<BpdtsUserDto> getUsersInLondon() {
    return callGetUsersInLondon().getBody();
  }

  public List<BpdtsUserDto> getUsers() {
    return callGetUsers().getBody();
  }

  ResponseEntity<List<BpdtsUserDto>> callGetUsers() {
    return restTemplate.exchange(
        GET_USERS, HttpMethod.GET, null, new ParameterizedTypeReference<List<BpdtsUserDto>>() {});
  }

  ResponseEntity<List<BpdtsUserDto>> callGetUsersInLondon() {
    return restTemplate.exchange(
        GET_LONDON_USERS,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<BpdtsUserDto>>() {});
  }
}

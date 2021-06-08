package uk.mcb.integration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceGateway {

  private static final String GET_LONDON_USERS =
      "http://dwp-techtest.herokuapp.com/city/London/users";
  private static final String GET_USERS = "http://dwp-techtest.herokuapp.com/users";

  private final RestTemplate restTemplate;

  public List<DwpUserDto> getUsersInLondon() {
    log.info("About to call get users in London");

    ResponseEntity<List<DwpUserDto>> responseEntity = callGetUsersInLondon();

    List<DwpUserDto> body = responseEntity.getBody();

    log.info("Finished call to get users in London");

    return body;
  }

  public List<DwpUserDto> getUsers() {
    log.info("About to call get users");

    ResponseEntity<List<DwpUserDto>> responseEntity = callGetUsers();

    List<DwpUserDto> body = responseEntity.getBody();

    log.info("Finished call to get users");

    return body;
  }

  ResponseEntity<List<DwpUserDto>> callGetUsers() {
    return restTemplate.exchange(
        GET_USERS, HttpMethod.GET, null, new ParameterizedTypeReference<List<DwpUserDto>>() {});
  }

  ResponseEntity<List<DwpUserDto>> callGetUsersInLondon() {
    return restTemplate.exchange(
        GET_LONDON_USERS,
        HttpMethod.GET,
        null,
        new ParameterizedTypeReference<List<DwpUserDto>>() {});
  }
}

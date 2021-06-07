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
      "http://dwp-techtest.herokuapp.com/city/London/users";
  private static final String GET_USERS = "http://dwp-techtest.herokuapp.com/users";

  private final RestTemplate restTemplate;

  public List<DwpUserDto> getUsersInLondon() {
    ResponseEntity<List<DwpUserDto>> responseEntity = callGetUsersInLondon();

    return responseEntity.getBody();
  }

  public List<DwpUserDto> getUsers() {
    ResponseEntity<List<DwpUserDto>> responseEntity = callGetUsers();

    return responseEntity.getBody();
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

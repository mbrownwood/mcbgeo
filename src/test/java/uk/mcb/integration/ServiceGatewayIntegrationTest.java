package uk.mcb.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceGatewayIntegrationTest {

  private ServiceGateway serviceGateway;

  @BeforeEach
  void beforeEach() {
    serviceGateway = new ServiceGateway(new RestTemplate());
  }

  @Test
  void callGetUsersInLondon() {
    ResponseEntity<List<DwpUserDto>> usersInLondonResponseEntity =
        serviceGateway.callGetUsersInLondon();
    assertThat(usersInLondonResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getUsersInLondon() {
    List<DwpUserDto> usersInLondon = serviceGateway.getUsersInLondon();
    assertThat(usersInLondon).isNotNull();
  }

  @Test
  void callGetUsers() {
    ResponseEntity<List<DwpUserDto>> usersResponseEntity = serviceGateway.callGetUsers();
    assertThat(usersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getUsers() {
    List<DwpUserDto> users = serviceGateway.getUsers();
    assertThat(users).isNotNull();
  }
}

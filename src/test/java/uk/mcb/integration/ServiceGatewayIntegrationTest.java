package uk.mcb.integration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

class ServiceGatewayIntegrationTest {

  private ServiceGateway serviceGateway;

  @BeforeEach
  void beforeEach() {
    serviceGateway = new ServiceGateway(new RestTemplate());
  }

  @Test
  void callGetUsersInLondon() {
    var usersInLondonResponseEntity = serviceGateway.callGetUsersInLondon();
    assertThat(usersInLondonResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getUsersInLondon() {
    var usersInLondon = serviceGateway.getUsersInLondon();
    assertThat(usersInLondon).isNotNull();
  }

  @Test
  void callGetUsers() {
    var usersResponseEntity = serviceGateway.callGetUsers();
    assertThat(usersResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
  }

  @Test
  void getUsers() {
    var users = serviceGateway.getUsers();
    assertThat(users).isNotNull();
  }
}

package uk.mcb.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.mcb.generated.rest.v1.dto.UserDto;
import uk.mcb.integration.BpdtsUserDto;
import uk.mcb.usecase.PeopleFinder;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class LondonAreaUsersApiAdapterTest {

  @Mock private PeopleFinder peopleFinder;

  private LondonAreaUsersApiAdapter londonAreaUsersApiAdapter;
  private ResponseEntity<List<UserDto>> londonAreaUsers;

  @BeforeEach
  void beforeEach() {
    londonAreaUsersApiAdapter = new LondonAreaUsersApiAdapter(peopleFinder);
  }

  @Nested
  class When_empty_list_of_users_found {

    @BeforeEach
    void beforeEach() {
      when(peopleFinder.execute()).thenReturn(emptyList());

      londonAreaUsers = londonAreaUsersApiAdapter.getUsers();
    }

    @Test
    void then_response_ok() {
      assertThat(londonAreaUsers.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void then_no_users_returned() {
      assertThat(londonAreaUsers.getBody()).hasSize(0);
    }
  }

  @Nested
  class When_list_of_users_found {

    @BeforeEach
    void beforeEach() {
      var bpdtsUserDtos =
          List.of(
              BpdtsUserDto.builder().build(),
              BpdtsUserDto.builder().id(1).build(),
              BpdtsUserDto.builder()
                  .id(2)
                  .firstName("Adam")
                  .lastName("Apple")
                  .email("adam@uk")
                  .ipAddress("1.1.1.1")
                  .latitude(0.0)
                  .longitude(1.0)
                  .build());

      when(peopleFinder.execute()).thenReturn(bpdtsUserDtos);

      londonAreaUsers = londonAreaUsersApiAdapter.getUsers();
    }

    @Test
    void then_response_ok() {
      assertThat(londonAreaUsers.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void then_list_of_users_returned() {
      assertThat(londonAreaUsers.getBody()).hasSize(3);
      verifyUser0(londonAreaUsers.getBody().get(0));
      verifyUser1(londonAreaUsers.getBody().get(1));
      verifyUser2(londonAreaUsers.getBody().get(2));
    }

    private void verifyUser0(UserDto userDto) {
      assertThat(userDto.getId()).isNull();
      assertThat(userDto.getFirstName()).isNull();
      assertThat(userDto.getLastName()).isNull();
      assertThat(userDto.getEmail()).isNull();
      assertThat(userDto.getIpAddress()).isNull();
      assertThat(userDto.getLatitude()).isNull();
      assertThat(userDto.getLongitude()).isNull();
    }

    private void verifyUser1(UserDto userDto) {
      assertThat(userDto.getId()).isEqualTo(1);
      assertThat(userDto.getFirstName()).isNull();
      assertThat(userDto.getLastName()).isNull();
      assertThat(userDto.getEmail()).isNull();
      assertThat(userDto.getIpAddress()).isNull();
      assertThat(userDto.getLatitude()).isNull();
      assertThat(userDto.getLongitude()).isNull();
    }

    private void verifyUser2(UserDto userDto) {
      assertThat(userDto.getId()).isEqualTo(2);
      assertThat(userDto.getFirstName()).isEqualTo("Adam");
      assertThat(userDto.getLastName()).isEqualTo("Apple");
      assertThat(userDto.getEmail()).isEqualTo("adam@uk");
      assertThat(userDto.getIpAddress()).isEqualTo("1.1.1.1");
      assertThat(userDto.getLatitude()).isEqualTo(0.0);
      assertThat(userDto.getLongitude()).isEqualTo(1.0);
    }
  }
}

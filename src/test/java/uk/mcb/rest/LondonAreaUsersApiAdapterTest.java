package uk.mcb.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.mcb.generated.rest.v1.dto.PeopleDto;
import uk.mcb.integration.DwpUserDto;
import uk.mcb.service.LondonAreaPeopleFinder;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class LondonAreaUsersApiAdapterTest {

  @Mock private LondonAreaPeopleFinder londonAreaPeopleFinder;

  private LondonAreaUsersApiAdapter londonAreaUsersApiAdapter;

  @BeforeEach
  void beforeEach() {
    londonAreaUsersApiAdapter = new LondonAreaUsersApiAdapter(londonAreaPeopleFinder);
  }

  @Test
  void when_empty_list_of_users_found_then_no_users_returned() {
    when(londonAreaPeopleFinder.findPeopleInLondonArea()).thenReturn(emptyList());

    ResponseEntity<List<PeopleDto>> londonAreaUsers =
        londonAreaUsersApiAdapter.getPeopleInLondonArea();

    assertThat(londonAreaUsers.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(londonAreaUsers.getBody()).isEmpty();
  }

  @Test
  void when_list_of_users_found_then_list_of_users_returned() {
    List<DwpUserDto> dwpUserDtos =
        List.of(
            DwpUserDto.builder().build(),
            DwpUserDto.builder().id(1).build(),
            DwpUserDto.builder()
                .id(2)
                .firstName("Adam")
                .lastName("Apple")
                .email("adam@uk")
                .ipAddress("1.1.1.1")
                .latitude(0.0)
                .longitude(1.0)
                .build());

    when(londonAreaPeopleFinder.findPeopleInLondonArea()).thenReturn(dwpUserDtos);

    ResponseEntity<List<PeopleDto>> londonAreaUsers =
        londonAreaUsersApiAdapter.getPeopleInLondonArea();

    assertThat(londonAreaUsers.getStatusCode()).isEqualTo(HttpStatus.OK);
    assertThat(londonAreaUsers.getBody()).hasSize(3);
    verifyUser0(londonAreaUsers.getBody().get(0));
    verifyUser1(londonAreaUsers.getBody().get(1));
    verifyUser2(londonAreaUsers.getBody().get(2));
  }

  private void verifyUser0(PeopleDto userDto) {
    assertThat(userDto.getId()).isNull();
    assertThat(userDto.getFirstName()).isNull();
    assertThat(userDto.getLastName()).isNull();
    assertThat(userDto.getEmail()).isNull();
    assertThat(userDto.getIpAddress()).isNull();
    assertThat(userDto.getLatitude()).isNull();
    assertThat(userDto.getLongitude()).isNull();
  }

  private void verifyUser1(PeopleDto userDto) {
    assertThat(userDto.getId()).isEqualTo(1);
    assertThat(userDto.getFirstName()).isNull();
    assertThat(userDto.getLastName()).isNull();
    assertThat(userDto.getEmail()).isNull();
    assertThat(userDto.getIpAddress()).isNull();
    assertThat(userDto.getLatitude()).isNull();
    assertThat(userDto.getLongitude()).isNull();
  }

  private void verifyUser2(PeopleDto userDto) {
    assertThat(userDto.getId()).isEqualTo(2);
    assertThat(userDto.getFirstName()).isEqualTo("Adam");
    assertThat(userDto.getLastName()).isEqualTo("Apple");
    assertThat(userDto.getEmail()).isEqualTo("adam@uk");
    assertThat(userDto.getIpAddress()).isEqualTo("1.1.1.1");
    assertThat(userDto.getLatitude()).isEqualTo(0.0);
    assertThat(userDto.getLongitude()).isEqualTo(1.0);
  }
}

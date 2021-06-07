package uk.mcb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.mcb.integration.DwpUserDto;
import uk.mcb.integration.ServiceGateway;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.mcb.Constants.LONDON_LATITUDE;
import static uk.mcb.Constants.LONDON_LONGITUDE;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class LondonAreaPeopleFinderTest {

  private static final Double NEW_YORK_LATITUDE = 40.712776;
  private static final Double NEW_YORK_LONGITUDE = -74.005974;

  @Mock private ServiceGateway serviceGateway;

  private LondonAreaPeopleFinder londonAreaPeopleFinder;

  @BeforeEach
  void beforeEach() {
    londonAreaPeopleFinder = new LondonAreaPeopleFinder(serviceGateway);
  }

  @Test
  void when_users_in_london_then_users_in_london_area() {
    List<DwpUserDto> usersInLondon = List.of(DwpUserDto.builder().id(1).build());

    when(serviceGateway.getUsersInLondon()).thenReturn(usersInLondon);

    List<DwpUserDto> usersInLondonArea = londonAreaPeopleFinder.findPeopleInLondonArea();

    assertThat(usersInLondonArea).hasSize(1);
    assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
  }

  @Test
  void when_users_within_fifty_miles_of_london_then_users_in_london_area() {
    List<DwpUserDto> users =
        List.of(
            DwpUserDto.builder()
                .id(1)
                .latitude(LONDON_LATITUDE)
                .longitude(LONDON_LONGITUDE)
                .build(),
            DwpUserDto.builder()
                .id(2)
                .latitude(NEW_YORK_LATITUDE)
                .longitude(NEW_YORK_LONGITUDE)
                .build());

    when(serviceGateway.getUsers()).thenReturn(users);

    List<DwpUserDto> usersInLondonArea = londonAreaPeopleFinder.findPeopleInLondonArea();

    assertThat(usersInLondonArea).hasSize(1);
    assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
  }

  @Test
  void when_users_in_london_and_users_within_fifty_miles_of_london_then_users_in_london_area() {
    List<DwpUserDto> usersInLondon = List.of(DwpUserDto.builder().id(1).build());
    List<DwpUserDto> users =
        List.of(
            DwpUserDto.builder()
                .id(2)
                .latitude(LONDON_LATITUDE)
                .longitude(LONDON_LONGITUDE)
                .build());

    when(serviceGateway.getUsersInLondon()).thenReturn(usersInLondon);
    when(serviceGateway.getUsers()).thenReturn(users);

    List<DwpUserDto> usersInLondonArea = londonAreaPeopleFinder.findPeopleInLondonArea();

    assertThat(usersInLondonArea).hasSize(2);
    assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
    assertThat((usersInLondonArea.get(1).getId())).isEqualTo(2);
  }

  @Test
  void
      when_users_in_london_and_users_in_fifty_miles_of_london_duplicated_then_users_in_london_area_no_duplicate() {
    List<DwpUserDto> usersInLondon = List.of(DwpUserDto.builder().id(1).build());
    List<DwpUserDto> users =
        List.of(
            DwpUserDto.builder()
                .id(1)
                .latitude(LONDON_LATITUDE)
                .longitude(LONDON_LONGITUDE)
                .build());

    when(serviceGateway.getUsersInLondon()).thenReturn(usersInLondon);
    when(serviceGateway.getUsers()).thenReturn(users);

    List<DwpUserDto> usersInLondonArea = londonAreaPeopleFinder.findPeopleInLondonArea();

    assertThat(usersInLondonArea).hasSize(1);
    assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
  }

  @Test
  void when_no_users_then_no_users_in_london_area() {
    assertThat(londonAreaPeopleFinder.findPeopleInLondonArea()).isEmpty();
  }
}

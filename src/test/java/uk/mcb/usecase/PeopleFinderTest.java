package uk.mcb.usecase;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.mcb.integration.BpdtsUserDto;
import uk.mcb.integration.ServiceGateway;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static uk.mcb.Constants.LONDON_LATITUDE;
import static uk.mcb.Constants.LONDON_LONGITUDE;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ExtendWith(MockitoExtension.class)
class PeopleFinderTest {

  private static final Double NEW_YORK_LATITUDE = 40.71;
  private static final Double NEW_YORK_LONGITUDE = 74.0;

  @Mock private ServiceGateway serviceGateway;

  private PeopleFinder peopleFinder;
  private List<BpdtsUserDto> usersInLondonArea;

  @BeforeEach
  void beforeEach() {
    peopleFinder = new PeopleFinder(serviceGateway);
    usersInLondonArea = new ArrayList<>();
  }

  @Nested
  class When_user_in_london {

    @BeforeEach
    void beforeEach() {
      var usersInLondon = List.of(BpdtsUserDto.builder().id(1).build());

      when(serviceGateway.getUsersInLondon()).thenReturn(usersInLondon);

      usersInLondonArea = peopleFinder.execute();
    }

    @Test
    void then_user_in_london_area() {
      assertThat(usersInLondonArea).hasSize(1);
      assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
    }
  }

  @Nested
  class When_user_in_fifty_mile_circle_of_london {

    @BeforeEach
    void beforeEach() {
      var users =
          List.of(
              BpdtsUserDto.builder()
                  .id(1)
                  .latitude(LONDON_LATITUDE)
                  .longitude(LONDON_LONGITUDE)
                  .build(),
              BpdtsUserDto.builder()
                  .id(2)
                  .latitude(NEW_YORK_LATITUDE)
                  .longitude(NEW_YORK_LONGITUDE)
                  .build());

      when(serviceGateway.getUsers()).thenReturn(users);

      usersInLondonArea = peopleFinder.execute();
    }

    @Test
    void then_user_in_london_area() {
      assertThat(usersInLondonArea).hasSize(1);
      assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
    }
  }

  @Nested
  class When_user_in_london_and_user_in_fifty_mile_area_of_london {

    @BeforeEach
    void beforeEach() {
      var usersInLondon = List.of(BpdtsUserDto.builder().id(1).build());
      var users =
          List.of(
              BpdtsUserDto.builder()
                  .id(2)
                  .latitude(LONDON_LATITUDE)
                  .longitude(LONDON_LONGITUDE)
                  .build());

      when(serviceGateway.getUsersInLondon()).thenReturn(usersInLondon);
      when(serviceGateway.getUsers()).thenReturn(users);

      usersInLondonArea = peopleFinder.execute();
    }

    @Test
    void then_users_in_london_area() {
      assertThat(usersInLondonArea).hasSize(2);
      assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
      assertThat((usersInLondonArea.get(1).getId())).isEqualTo(2);
    }
  }

  @Nested
  class When_user_in_london_and_user_in_fifty_mile_area_of_london_duplicated {

    @BeforeEach
    void beforeEach() {
      var usersInLondon = List.of(BpdtsUserDto.builder().id(1).build());
      var users =
          List.of(
              BpdtsUserDto.builder()
                  .id(1)
                  .latitude(LONDON_LATITUDE)
                  .longitude(LONDON_LONGITUDE)
                  .build());

      when(serviceGateway.getUsersInLondon()).thenReturn(usersInLondon);
      when(serviceGateway.getUsers()).thenReturn(users);

      usersInLondonArea = peopleFinder.execute();
    }

    @Test
    void then_user_in_london_area_no_dulpicate() {
      assertThat(usersInLondonArea).hasSize(1);
      assertThat((usersInLondonArea.get(0).getId())).isEqualTo(1);
    }
  }

  @Nested
  class When_no_users {

    @Test
    void then_no_users_in_london_area() {
      assertThat(peopleFinder.execute()).isEmpty();
    }
  }
}

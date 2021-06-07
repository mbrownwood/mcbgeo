package uk.mcb.service;

import com.tomtom.speedtools.geometry.GeoCircle;
import com.tomtom.speedtools.geometry.GeoPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.mcb.integration.DwpUserDto;
import uk.mcb.integration.ServiceGateway;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uk.mcb.Constants.LONDON_LATITUDE;
import static uk.mcb.Constants.LONDON_LONGITUDE;
import static uk.mcb.Constants.METRES_PER_MILE;
import static uk.mcb.Constants.RADIUS_IN_MILES;

@Service
@RequiredArgsConstructor
public class LondonAreaPeopleFinder {

  private static final double FIFTY_MILES_IN_METRES = RADIUS_IN_MILES * METRES_PER_MILE;

  private final ServiceGateway serviceGateway;

  public List<DwpUserDto> findPeopleInLondonArea() {
    List<DwpUserDto> usersInLondon = serviceGateway.getUsersInLondon();

    List<DwpUserDto> usersInFiftyMileCircleOfLondon = getUsersInFiftyMileCircleOfLondon();

    return getDistinctUsers(usersInLondon, usersInFiftyMileCircleOfLondon);
  }

  private List<DwpUserDto> getUsersInFiftyMileCircleOfLondon() {
    List<DwpUserDto> users = serviceGateway.getUsers();

    return users.stream().filter(this::isInFiftyMileCircleOfLondon).collect(Collectors.toList());
  }

  private boolean isInFiftyMileCircleOfLondon(DwpUserDto userDto) {
    GeoPoint geoPointOfLondon = new GeoPoint(LONDON_LATITUDE, LONDON_LONGITUDE);

    GeoCircle fiftyMileCircleOfLondon = new GeoCircle(geoPointOfLondon, FIFTY_MILES_IN_METRES);

    GeoPoint geoPointOfUser = new GeoPoint(userDto.getLatitude(), userDto.getLongitude());

    return fiftyMileCircleOfLondon.contains(geoPointOfUser);
  }

  private List<DwpUserDto> getDistinctUsers(
      List<DwpUserDto> usersInLondon, List<DwpUserDto> usersInFiftyMileCircleOfLondon) {
    Map<Object, Boolean> distinctUserIds = new HashMap<>();

    return Stream.of(usersInLondon, usersInFiftyMileCircleOfLondon)
        .flatMap(Collection::stream)
        .filter(user -> distinctUserIds.putIfAbsent(user.getId(), Boolean.TRUE) == null)
        .collect(Collectors.toList());
  }
}

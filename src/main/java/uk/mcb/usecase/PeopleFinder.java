package uk.mcb.usecase;

import com.tomtom.speedtools.geometry.GeoCircle;
import com.tomtom.speedtools.geometry.GeoPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uk.mcb.integration.BpdtsUserDto;
import uk.mcb.integration.ServiceGateway;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static uk.mcb.Constants.LONDON_LATITUDE;
import static uk.mcb.Constants.LONDON_LONGITUDE;
import static uk.mcb.Constants.METRES_PER_MILE;
import static uk.mcb.Constants.RADIUS_IN_MILES;
import static uk.mcb.Utils.distinctByKey;

@Service
@RequiredArgsConstructor
public class PeopleFinder {

  private final ServiceGateway serviceGateway;

  public List<BpdtsUserDto> execute() {
    return usersInLondonArea();
  }

  private List<BpdtsUserDto> usersInLondonArea() {
    return Stream.of(usersInLondon(), usersInFiftyMileCircleOfLondon())
        .flatMap(Collection::stream)
        .filter(distinctByKey(BpdtsUserDto::getId))
        .collect(Collectors.toList());
  }

  private List<BpdtsUserDto> usersInLondon() {
    return serviceGateway.getUsersInLondon();
  }

  private List<BpdtsUserDto> usersInFiftyMileCircleOfLondon() {
    var fiftyMileCircleOfLondon = new GeoCircle(london(), fiftyMileRadiusInMetres());

    return users().stream()
        .filter(user -> fiftyMileCircleOfLondon.contains(geoPointOf(user)))
        .collect(Collectors.toList());
  }

  private GeoPoint london() {
    return new GeoPoint(LONDON_LATITUDE, LONDON_LONGITUDE);
  }

  private Double fiftyMileRadiusInMetres() {
    return RADIUS_IN_MILES * METRES_PER_MILE;
  }

  private List<BpdtsUserDto> users() {
    return serviceGateway.getUsers();
  }

  private GeoPoint geoPointOf(BpdtsUserDto user) {
    return new GeoPoint(user.getLatitude(), user.getLongitude());
  }
}

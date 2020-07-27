package uk.mcb.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uk.mcb.generated.rest.v1.DefaultApi;
import uk.mcb.generated.rest.v1.dto.UserDto;
import uk.mcb.integration.BpdtsUserDto;
import uk.mcb.usecase.PeopleFinder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LondonAreaUsersApiAdapter implements DefaultApi {

  private final PeopleFinder peopleFinder;

  @Override
  public ResponseEntity<List<UserDto>> getUsers() {

    var bpdtsUserDtos = peopleFinder.execute();

    var userDtos = mapToUserDtos(bpdtsUserDtos);

    return ResponseEntity.ok(userDtos);
  }

  private List<UserDto> mapToUserDtos(List<BpdtsUserDto> bpdtsUserDtos) {
    var userDtos = new ArrayList<UserDto>();
    for (BpdtsUserDto bpdtsUserDto : bpdtsUserDtos) {
      userDtos.add(
          new UserDto()
              .id(bpdtsUserDto.getId())
              .firstName(bpdtsUserDto.getFirstName())
              .lastName(bpdtsUserDto.getLastName())
              .email(bpdtsUserDto.getEmail())
              .ipAddress(bpdtsUserDto.getIpAddress())
              .latitude(bpdtsUserDto.getLatitude())
              .longitude(bpdtsUserDto.getLongitude()));
    }
    return userDtos;
  }
}

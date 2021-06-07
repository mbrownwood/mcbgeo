package uk.mcb.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uk.mcb.generated.rest.v1.V1Api;
import uk.mcb.generated.rest.v1.dto.PeopleDto;
import uk.mcb.integration.DwpUserDto;
import uk.mcb.service.LondonAreaPeopleFinder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class LondonAreaUsersApiAdapter implements V1Api {

  private final LondonAreaPeopleFinder londonAreaPeopleFinder;

  @Override
  public ResponseEntity<List<PeopleDto>> getPeopleInLondonArea() {
    List<DwpUserDto> dwpUserDtos = londonAreaPeopleFinder.findPeopleInLondonArea();

    List<PeopleDto> peopleDtos = mapToPeopleDtos(dwpUserDtos);

    return ResponseEntity.ok(peopleDtos);
  }

  private List<PeopleDto> mapToPeopleDtos(List<DwpUserDto> dwpUserDtos) {
    List<PeopleDto> peopleDtos = new ArrayList<>();

    for (DwpUserDto dwpUserDto : dwpUserDtos) {
      peopleDtos.add(
          new PeopleDto()
              .id(dwpUserDto.getId())
              .firstName(dwpUserDto.getFirstName())
              .lastName(dwpUserDto.getLastName())
              .email(dwpUserDto.getEmail())
              .ipAddress(dwpUserDto.getIpAddress())
              .latitude(dwpUserDto.getLatitude())
              .longitude(dwpUserDto.getLongitude()));
    }
    return peopleDtos;
  }
}

package uk.mcb.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import uk.mcb.generated.rest.v1.V1Api;
import uk.mcb.generated.rest.v1.dto.PeopleDto;
import uk.mcb.integration.DwpUserDto;
import uk.mcb.service.LondonAreaPeopleFinder;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LondonAreaUsersApiAdapter implements V1Api {

  private final LondonAreaPeopleFinder londonAreaPeopleFinder;

  @Override
  public ResponseEntity<List<PeopleDto>> getPeopleInLondonArea() {
    log.info("About to process request to get people in London area");

    List<DwpUserDto> dwpUserDtos = londonAreaPeopleFinder.findPeopleInLondonArea();

    List<PeopleDto> peopleDtos = mapToPeopleDtos(dwpUserDtos);

    ResponseEntity<List<PeopleDto>> responseEntity = ResponseEntity.ok(peopleDtos);

    log.info("Finished processing request to get people in London area");

    return responseEntity;
  }

  private List<PeopleDto> mapToPeopleDtos(List<DwpUserDto> dwpUserDtos) {
    log.info("About to map to people dtos");

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
    log.info("Finished mapping to people dtos");

    return peopleDtos;
  }
}

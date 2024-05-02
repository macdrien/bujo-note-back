package fr.sidranie.mapper;

import fr.sidranie.domain.User;
import fr.sidranie.dto.RegistrationDto;

public class RegistrationMapper {
  public static User registrationDtoToUser(RegistrationDto registrationDto) {
    return new User(
      registrationDto.getUsername(),
      registrationDto.getFamilyName(),
      registrationDto.getGivenName(),
      registrationDto.getEmail(),
      registrationDto.getPassword()
    );
  }
}

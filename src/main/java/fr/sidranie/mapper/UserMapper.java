package fr.sidranie.mapper;

import fr.sidranie.domain.User;
import fr.sidranie.dto.UserDto;

public class UserMapper {

  public static UserDto userToUserDto(User user) {
    return user == null ? null :
      new UserDto(user.username,
        user.familyName,
        user.givenName,
        user.email);
  }
}

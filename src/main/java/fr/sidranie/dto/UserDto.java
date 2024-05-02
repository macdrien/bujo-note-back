package fr.sidranie.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class UserDto {
  public final String username;
  public final String familyName;
  public final String givenName;
  public final String email;

  public UserDto(String username, String familyName, String givenName, String email) {
    this.username = username;
    this.familyName = familyName;
    this.givenName = givenName;
    this.email = email;
  }
}

package fr.sidranie.dto;

public class RegistrationDto {
  private String username;
  private String familyName;
  private String givenName;
  private String email;
  private String password;

  public RegistrationDto() {
  }

  public RegistrationDto(String username, String familyName, String givenName, String email, String password) {
    this.username = username;
    this.familyName = familyName;
    this.givenName = givenName;
    this.email = email;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFamilyName() {
    return familyName;
  }

  public void setFamilyName(String familyName) {
    this.familyName = familyName;
  }

  public String getGivenName() {
    return givenName;
  }

  public void setGivenName(String givenName) {
    this.givenName = givenName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}

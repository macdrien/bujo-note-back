package fr.sidranie.dto;

import io.quarkus.security.credential.Credential;

public class CredentialDto implements Credential {
  private String name;
  private String password;

  public CredentialDto() {
  }

  public CredentialDto(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}

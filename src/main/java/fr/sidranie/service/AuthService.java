package fr.sidranie.service;

import java.security.Principal;

import fr.sidranie.dto.CredentialDto;
import fr.sidranie.dto.RegistrationDto;
import fr.sidranie.dto.TokenDto;
import fr.sidranie.dto.UserDto;

public interface AuthService {
  
  public UserDto findMe(Principal principal);
  public TokenDto login(CredentialDto credentials);
  public void register(RegistrationDto registration);
}

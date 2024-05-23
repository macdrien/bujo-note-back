package fr.sidranie.service.impl;

import java.security.Principal;

import io.quarkus.runtime.util.HashUtil;
import io.quarkus.security.UnauthorizedException;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

import fr.sidranie.domain.User;
import fr.sidranie.dto.CredentialDto;
import fr.sidranie.dto.RegistrationDto;
import fr.sidranie.dto.TokenDto;
import fr.sidranie.dto.UserDto;
import fr.sidranie.mapper.UserMapper;
import fr.sidranie.service.AuthService;

@ApplicationScoped
public class AuthServiceImpl implements AuthService {

  @Override
  public UserDto findMe(Principal principal) throws UnauthorizedException {
    if (principal == null) {
      throw new UnauthorizedException();
    }

    try {
      User user = User.findByName(principal.getName());
      return UserMapper.userToUserDto(user);

    } catch (NoResultException exception) {
      throw new UnauthorizedException();
    }
  }

  @Override
  public TokenDto login(CredentialDto credential) throws UnauthorizedException {
    if (credential == null) {
      throw new UnauthorizedException("No credentials");
    }

    User user = User.findByName(credential.getName());

    if (user == null) {
      throw new UnauthorizedException();
    }

    String givenPassword = HashUtil.sha512(credential.getPassword());

    if (!user.password.equals(givenPassword)) {
      throw new UnauthorizedException();
    }

    return new TokenDto(Jwt.issuer("https://bujo-note-backend.fr/issuer")
        .upn(user.id.toString())
        .sign());
  }

  @Override
  public void register(RegistrationDto registration) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'register'");
  }
  
}

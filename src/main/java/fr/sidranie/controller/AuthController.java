package fr.sidranie.controller;

import java.net.URI;
import java.security.Principal;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.panache.common.Parameters;
import io.quarkus.security.Authenticated;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

import fr.sidranie.domain.User;
import fr.sidranie.dto.CredentialDto;
import fr.sidranie.dto.RegistrationDto;
import fr.sidranie.mapper.RegistrationMapper;
import fr.sidranie.mapper.UserMapper;

@Path("/auth")
@RequestScoped
public class AuthController {

  @Inject
  JsonWebToken token;

  @GET
  @Path("/me")
  @Authenticated
  public Response me(@Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    if (principal == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    return Response.ok(UserMapper.userToUserDto(User.findByName(principal.getName()))).build();
  }

  @POST
  @Path("/login")
  public Response login(CredentialDto credential) {
    User user = User.findByName(credential.getName());

    if (user == null || !user.password.equals(credential.getPassword())) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    String token = Jwt.issuer("https://bujo-note-backend.fr/issuer")
        .upn(credential.getName())
        .sign();
    return Response.ok(token).build();
  }

  @POST
  @Path("/register")
  @PermitAll
  @Transactional
  public Response register(RegistrationDto registration) {
    User user = RegistrationMapper.registrationDtoToUser(registration);
    user.persist();
    System.out.println(User.find("username = 'test'", new Parameters()).count());
    return Response.created(URI.create("/auth/me")).build();
  }
}

package fr.sidranie.controller;

import java.net.URI;
import java.security.Principal;

import org.eclipse.microprofile.jwt.JsonWebToken;

import io.quarkus.security.Authenticated;
import io.quarkus.security.UnauthorizedException;
import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

import fr.sidranie.domain.User;
import fr.sidranie.dto.CredentialDto;
import fr.sidranie.dto.RegistrationDto;
import fr.sidranie.dto.UserDto;
import fr.sidranie.mapper.RegistrationMapper;
import fr.sidranie.service.AuthService;

@Path("/auth")
@RequestScoped
public class AuthController {

  @Inject
  JsonWebToken token;

  @Inject
  AuthService authService;

  @GET
  @Path("/me")
  @Authenticated
  public Response me(@Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    ResponseBuilder response;

    if (principal == null) {
      response = Response.status(Status.UNAUTHORIZED);
    }

    try {
      UserDto user = authService.findMe(principal);
      response = Response.ok(user);

    } catch (UnauthorizedException exception) {
      response = Response.status(Status.UNAUTHORIZED);
    }

    return response.build();
  }

  @POST
  @Path("/login")
  public Response login(CredentialDto credential) {
    try {
      return Response.ok(authService.login(credential)).build();
    } catch (UnauthorizedException exception) {
      return Response.status(Status.UNAUTHORIZED).build();
    }
  }

  @POST
  @Path("/register")
  @PermitAll
  @Transactional
  public Response register(RegistrationDto registration) {
    User user = RegistrationMapper.registrationDtoToUser(registration);
    user.persist();
    return Response.created(URI.create("/auth/me")).build();
  }
}

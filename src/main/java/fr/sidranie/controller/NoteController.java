package fr.sidranie.controller;

import java.net.URI;
import java.security.Principal;

import io.quarkus.security.Authenticated;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;

import fr.sidranie.dto.CreateNoteDto;
import fr.sidranie.dto.NoteDto;
import fr.sidranie.service.NoteService;

@Path("/notes")
@RequestScoped
public class NoteController {

  @Inject
  NoteService service;
  
  @GET
  @Authenticated
  public Response listNotes(@Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    if (principal == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    return Response.ok(service.getNotesByUser(principal)).build();
  }
  
  @GET
  @Path("{id}")
  @Authenticated
  public Response findNote(@PathParam("id") Long id, @Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    if (principal == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }
    
    return Response.ok(service.getNote(id, principal)).build();
  }
  
    @POST
    @Authenticated
    @Transactional
    public Response createNote(CreateNoteDto createNote, @Context SecurityContext context) {
      Principal principal = context.getUserPrincipal();
      if (principal == null) {
        return Response.status(Status.UNAUTHORIZED).build();
      }
      NoteDto newNote = service.createNote(createNote, principal);
  
      return Response.created(URI.create(String.format("/notes/%d", newNote.id))).build();
    }

  @PATCH
  @Path("{id}")
  @Authenticated
  @Transactional
  public Response updateNote(@PathParam("id") Long id, NoteDto updates, @Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    if (principal == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    try {
      return Response.ok(service.updateNote(id, updates, principal)).build();
    } catch (NotFoundException exception) {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @DELETE
  @Path("{id}")
  @Authenticated
  @Transactional
  public Response deleteNote(@PathParam("id") Long id, @Context SecurityContext context) {
    Principal principal = context.getUserPrincipal();
    if (principal == null) {
      return Response.status(Status.UNAUTHORIZED).build();
    }

    return Response.status(service.deleteNote(id, principal) ? Status.NO_CONTENT : Status.NOT_FOUND).build();
  }
}

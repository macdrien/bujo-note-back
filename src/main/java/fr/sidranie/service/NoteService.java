package fr.sidranie.service;

import java.security.Principal;
import java.util.List;

import jakarta.ws.rs.NotFoundException;

import fr.sidranie.dto.CreateNoteDto;
import fr.sidranie.dto.NoteDto;

public interface NoteService {
  public List<NoteDto> getNotesByUser(Principal principal);
  public NoteDto getNote(Long id, Principal principal) throws NotFoundException;
  public NoteDto createNote(CreateNoteDto creationDto, Principal principal);
  public NoteDto updateNote(Long id, NoteDto updates, Principal principal) throws NotFoundException;
  public boolean deleteNote(Long id, Principal principal);
}

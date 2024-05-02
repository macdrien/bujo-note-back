package fr.sidranie.service.impl;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.NotFoundException;

import fr.sidranie.domain.Note;
import fr.sidranie.domain.User;
import fr.sidranie.dto.CreateNoteDto;
import fr.sidranie.dto.NoteDto;
import fr.sidranie.mapper.NoteMapper;
import fr.sidranie.service.NoteService;

@ApplicationScoped
public class NoteServiceImpl implements NoteService {

  @Override
  public List<NoteDto> getNotesByUser(Principal principal) {
    User requester = User.findByName(principal.getName());
    List<Note> notes = Note.findByUser(requester.id);
    return notes.stream().map(NoteMapper::noteToNoteDto).toList();
  }

  @Override
  public NoteDto getNote(Long id, Principal principal) throws NotFoundException {
    User requester = User.findByName(principal.getName());
    Note note = Note.findById(id);

    if (note == null || note.owner == null || note.owner.id != requester.id) {
      throw new NotFoundException();
    }

    return NoteMapper.noteToNoteDto(note);
  }

  @Override
  public NoteDto createNote(CreateNoteDto creationDto, Principal principal) {
    User creator = User.findByName(principal.getName());
    Note note = new Note(creationDto.title, creationDto.text, creator, Instant.now(), null);
    note.persist();
    return NoteMapper.noteToNoteDto(note);
  }

  @Override
  public NoteDto updateNote(Long id, NoteDto updates, Principal principal) throws NotFoundException {
    User requester = User.findByName(principal.getName());
    Note note = Note.findById(id);

    if (note == null || note.owner == null || note.owner.id != requester.id) {
      throw new NotFoundException();
    }

    if (updates.title != null) {
      note.title = updates.title;
    }

    if (updates.text != null) {
      note.text = updates.text;
    }

    note.updatedAt = Instant.now();

    note.persist();

    return NoteMapper.noteToNoteDto(note);
  }

  @Override
  public boolean deleteNote(Long id, Principal principal) {
    User requester = User.findByName(principal.getName());
    Note note = Note.findById(id);

    if (note == null || note.owner == null || note.owner.id != requester.id) {
      return false;
    }

    return Note.deleteById(id);
  }
}

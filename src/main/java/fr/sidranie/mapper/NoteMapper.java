package fr.sidranie.mapper;

import fr.sidranie.domain.Note;
import fr.sidranie.dto.NoteDto;

public class NoteMapper {
  public static NoteDto noteToNoteDto(Note note) {
    return note == null ? null : new NoteDto(note.id, note.title, note.text, note.createdAt, note.updatedAt);
  }
}

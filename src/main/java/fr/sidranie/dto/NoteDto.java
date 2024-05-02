package fr.sidranie.dto;

import java.io.Serializable;
import java.time.Instant;

public class NoteDto implements Serializable {

  public Long id;
  public String title;
  public String text;
  public Instant createdAt;
  public Instant updatedAt;

  public NoteDto() {
  }

  public NoteDto(Long id, String title, String text, Instant createdAt, Instant updatedAt) {
    this.id = id;
    this.title = title;
    this.text = text;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }
}

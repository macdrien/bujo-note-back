package fr.sidranie.domain;

import java.time.Instant;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "note")
public class Note extends PanacheEntity {
  public String title;
  public String text;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_user")
  public User owner;

  public Instant createdAt;
  public Instant updatedAt;

  public Note() {}

  public Note(String title, String text, User owner, Instant createdAt, Instant updatedAt) {
    this.title = title;
    this.text = text;
    this.owner = owner;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static List<Note> findByUser(Long ownerId) {
    Parameters filter = Parameters.with("ownerId", ownerId);
    return find("owner.id = :ownerId", filter).list();
  }
}

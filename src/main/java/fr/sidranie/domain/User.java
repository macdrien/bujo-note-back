package fr.sidranie.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Parameters;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Table(name = "person")
public class User extends PanacheEntity {

  public String username;
  public String familyName;
  public String givenName;
  public String email;
  public String password;
  
  @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
  public List<Note> notes;

  public User() {
  }

  public User(String username, String familyName, String givenName, String email, String password) {
    super();
    this.username = username;
    this.familyName = familyName;
    this.givenName = givenName;
    this.email = email;
    this.password = password;
  }

  public static User findByName(String name) {
    return find("username = :name or email = :name", Parameters.with("name", name)).singleResult();
  }
}

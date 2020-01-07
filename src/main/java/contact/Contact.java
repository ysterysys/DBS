package contact;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Contact {

  private @Id @GeneratedValue Long id;
  private String name;
  private String role = "default";

  Contact() {}

  Contact(String name, String role) {
    this.name = name;
    this.role = role;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

public void setId(Long id2) {
    this.id = id2;
}

public String getRole() {
    return role;
}

public void setRole(String role) {
    this.role = role;
}

  public Long getId() {
    return id;
  }

}
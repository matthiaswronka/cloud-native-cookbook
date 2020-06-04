package de.nordlb.example.person;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "date_of_birth")
  private LocalDate dateOfBirth;

  @Column(name = "email")
  private String email;

  @Column(name = "motto")
  private String motto;


  public Person() {
  }

  public Person(String name, LocalDate dateOfBirth) {
    this.name = name;
    this.dateOfBirth = dateOfBirth;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getDateOfBirth() {
    return dateOfBirth;
  }

  public void setDateOfBirth(LocalDate dateOfBirth) {
    this.dateOfBirth = dateOfBirth;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Person person = (Person) o;

    if (id != null ? !id.equals(person.id) : person.id != null) {
      return false;
    }
    if (name != null ? !name.equals(person.name) : person.name != null) {
      return false;
    }
    if (dateOfBirth != null ? !dateOfBirth.equals(person.dateOfBirth)
        : person.dateOfBirth != null) {
      return false;
    }
    if (email != null ? !email.equals(person.email) : person.email != null) {
      return false;
    }
    return motto != null ? motto.equals(person.motto) : person.motto == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (dateOfBirth != null ? dateOfBirth.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (motto != null ? motto.hashCode() : 0);
    return result;
  }
}

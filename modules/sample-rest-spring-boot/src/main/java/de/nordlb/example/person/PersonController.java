package de.nordlb.example.person;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

  @Autowired
  private PersonRepository repository;

  @GetMapping("/persons")
  public List<Person> getPersons(@RequestParam(name = "name", required = false) String name) {
    if (Strings.isNotEmpty(name)) {
      System.out.println("looking for specific persons. name: " + name);
      return repository.findByName(name);
    } else {
      System.out.println("looking for all persons.");
      return StreamSupport.stream(
          repository.findAll().spliterator(), false)
          .collect(Collectors.toList());
    }
  }
}

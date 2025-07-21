package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;





@RestController
@RequestMapping("/api/persons")
public class PersonController {

    final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
    
    @GetMapping("")
    public ResponseEntity<List<Person>> getAllPersons() {
        return new ResponseEntity<>(personRepository.findAll(), HttpStatus.OK);
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> getPerson(@PathVariable Long id){
        
        Optional<Person> person = personRepository.findById(id);

        return person.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<?> createPerson(@RequestBody Person person) {
        Person personCreated = personRepository.save(person);
        
        return new ResponseEntity<>(personCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            Person existingPerson = person.get();
            existingPerson.setCity(personDetails.getCity());
            existingPerson.setName(personDetails.getName());
            existingPerson.setPhone(personDetails.getPhone());

            Person updatedPerson = personRepository.save(existingPerson);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id){
        Optional<Person> person = personRepository.findById(id);
        if(person.isPresent()){
            Person existingPerson = person.get();
            personRepository.delete(existingPerson);
            return new ResponseEntity<Void>(HttpStatus.OK);
        }

        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
    }
    

    
}

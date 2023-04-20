package com.in28minutes.rest.webservices.restfulwebservices.user;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import jakarta.validation.Valid;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class UserResource {
    //@Autowired
    private UserDaoService service;

    public UserResource(UserDaoService service){
        this.service= service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers(){
        return service.findAll();
    }

    /*@GetMapping("/users/{id}")
    public User retrieveUsers(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null)
             throw new UserNotFoundException("id : " + id);

        return user;
    }*/

    @GetMapping("/users/{id}")
    public EntityModel<User> retrieveUsers(@PathVariable int id){
        User user = service.findOne(id);

        if (user == null)
            throw new UserNotFoundException("id : " + id);
        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAllUsers());
        entityModel.add(link.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user){
        User savedUser = service.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public void DeleteUsers(@PathVariable int id){
        service.DeleteById(id);
    }

}

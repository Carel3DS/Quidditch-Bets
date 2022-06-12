package es.dws.quidditch.controller;

import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RequestMapping("/api")
@RestController
public class RESTController {

    @Autowired
    private UserRepository users;

    //USER REST (Does not have any sense, risk of critical data leak)
    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> postUser(@RequestBody User user){
        users.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name){
        Optional<User> op=users.findByName(name);
        return op.map(
                user -> new ResponseEntity<>(user, HttpStatus.OK)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    @PutMapping("/user/{name}")
    public ResponseEntity<User> putUser(@PathVariable String name,@RequestBody User user){
        Optional<User> oldUser=users.findByName(name);
        if(oldUser.isPresent()){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{name}")
    public ResponseEntity<User> deleteUser(@PathVariable String name){
        Optional<User> op=users.deleteUserByName(name);
        return op.map(
                user -> new ResponseEntity<>(user, HttpStatus.OK)
        ).orElseGet(
                () -> new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    //REST BET
}

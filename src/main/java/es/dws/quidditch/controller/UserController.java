package es.dws.quidditch.controller;

import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repository;

    public void profile(){}
    public void users(){}
    public void create(User user){
        repository.save(user);
    }
    public ResponseEntity<User> show (@PathVariable long id){
        Optional<User> op = repository.findById(id);
        if(op.isPresent()){
            User user = op.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    public void edit(User user, User newUser){}
    public void delete(User user){}
}

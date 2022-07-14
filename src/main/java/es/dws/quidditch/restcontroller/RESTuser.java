package es.dws.quidditch.restcontroller;

import es.dws.quidditch.model.User;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/api")
@RestController
public class RESTuser {
    @Autowired
    private UserService userService;

    @PostMapping("/user")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> postUser(@RequestBody User user){
        this.userService.post(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/{name}")
    public ResponseEntity<User> getUser(@PathVariable String name){
        User user = this.userService.get(name);
        if(user!=null) {
            return new  ResponseEntity<>(user, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/user/{name}")
    public ResponseEntity<User> putUser(@PathVariable String name, @RequestBody User user){
        if(this.userService.exists(name)){
            this.userService.put(name,user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{name}")
    public ResponseEntity<User> deleteUser(@PathVariable String name){
        if(this.userService.get(name) != null){
            User deleted = this.userService.delete(name);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

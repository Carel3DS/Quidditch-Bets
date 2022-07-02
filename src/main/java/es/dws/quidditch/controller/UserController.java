package es.dws.quidditch.controller;

import es.dws.quidditch.model.User;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/new_user")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model, User user){
        this.service.post(user);
        return "welcome";
    }
    //TODO: implement 'null' case
    @GetMapping("/profile/{id}")
    public String profile (Model model, @PathVariable String id){
        User user = this.service.get(id);
        return "user";
    }
    @GetMapping("/login")
    public String getLogin (Model model, @PathVariable String id){
        User user = this.service.get(id);
        return "user";
    }
    @PostMapping("/login")
    public String login (Model model, @PathVariable String id){
        User user = this.service.get(id);
        return "user";
    }
    @GetMapping("/register")
    public String getRegister (Model model, @PathVariable String id){
        User user = this.service.get(id);
        return "register";
    }
    @PostMapping("/register")
    public String register (Model model, @RequestBody User user){
        this.service.post(user);
        return "user";
    }
    @PostMapping("/edit_user")
    @ResponseStatus(HttpStatus.OK)
    public String edit(Model model,String userID, User newUser){
        this.service.put(userID,newUser);
        return "confirm";
    }
    @PostMapping("/delete_user")
    @ResponseStatus(HttpStatus.OK)
    public String delete(Model model,String userID){
        this.service.delete(userID);
        return "confirm";
    }
}

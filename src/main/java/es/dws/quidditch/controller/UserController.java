package es.dws.quidditch.controller;

import es.dws.quidditch.model.User;
import es.dws.quidditch.service.LocaleService;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private LocaleService localeService;
    @Autowired
    private UserService service;

    @PostMapping("/new_user")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Model model, User user){
        this.service.post(user);
        return "welcome";
    }

    @GetMapping("/profile/{name}")
    public String profile (Model model, @PathVariable String name, HttpServletRequest request){
        User user = this.service.get(name);
        model.addAttribute("user",user);
        if(request.getUserPrincipal().getName().equals(user.getName())){
            model.addAttribute("selfuser",user.getBets());
        }
        model.addAttribute("admin",request.isUserInRole("ADMIN"));

        return "user";
    }
    @GetMapping("/edituser")
    public String edit (Model model, HttpServletRequest request){
        User user = this.service.get(request.getUserPrincipal().getName());
        model.addAttribute("user",user);
        return "edit_user";
    }


    @PostMapping("/edit_user")
    @ResponseStatus(HttpStatus.OK)
    public String edit(Model model,String userID, User newUser){
        if(!this.service.get(userID).getLocale().getName().equals(newUser.getLocale().getName())){
            this.service.put(userID,newUser);
            newUser.setName(userID);
            this.localeService.put(newUser.getLocale().getId(),newUser);
        }else{
            this.service.put(userID,newUser);
            newUser.setName(userID);
        }
        return "/profile/"+userID;
    }

    //TODO: implement login template as login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String getRegister (){
        return "register";
    }
    @PostMapping("/register")
    public String register (@RequestBody User user, HttpSession session){
        this.service.post(user);
        session.setAttribute("user",user);  //Once registered, assign the new user to the session
        return "/";
    }
    //TODO: Implement 'logout' as redirect to home page
    @PostMapping("/delete_user")
    @ResponseStatus(HttpStatus.OK)
    public String delete(Model model,String userID){
        this.service.delete(userID);
        model.addAttribute("confirm","Usuario eliminado");
        model.addAttribute("confirm_message","Se ha eliminado el usuario");
        return "confirm";
    }
}

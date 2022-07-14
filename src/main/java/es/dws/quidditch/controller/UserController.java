package es.dws.quidditch.controller;

import es.dws.quidditch.model.Game;
import es.dws.quidditch.model.Locale;
import es.dws.quidditch.model.User;
import es.dws.quidditch.service.GameService;
import es.dws.quidditch.service.LocaleService;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collection;
import java.util.Objects;

@Controller
public class UserController {
    @Autowired
    private LocaleService localeService;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService service;

    @GetMapping("/")
    public String index(Model model){
        Collection<Game> games = gameService.get();
        Collection<Locale> locales = localeService.get();
        model.addAttribute("games",games);
        model.addAttribute("locales",locales);
        return "index";
    }
    @GetMapping("/nav")
    public String nav(Model model, HttpSession session){
        User u = (User)session.getAttribute("user");
        model.addAttribute("anonymous",u==null);
        model.addAttribute("user", Objects.requireNonNullElse(u, false));
        return "navbar";
    }

    @GetMapping("/user/{name}")
    public String profile (Model model, @PathVariable String name, HttpSession session){
        User user = this.service.get(name);
        model.addAttribute("user",user);
        User self = (User)session.getAttribute("user");
        if(self.getName().equals(user.getName())){
            model.addAttribute("selfuser",user.getBets());
        }
        //model.addAttribute("admin",request.isUserInRole("ADMIN"));

        return "user";
    }
    @GetMapping("/edituser")
    public String edit (Model model, HttpServletRequest request){
        if(request.getUserPrincipal() != null){
            User user = this.service.get(request.getUserPrincipal().getName());
            model.addAttribute("user",user);
        }

        return "edituser";
    }


    @PostMapping("/edituser")
    @ResponseStatus(HttpStatus.OK)
    public String edit(Model model,String userID, User newUser,HttpSession session){
        User user = this.service.get(userID);
        if(user.getLocale().getId() != newUser.getLocale().getId()){
            localeService.removeUser(user.getLocale().getId(),userID);
            newUser.setName(userID);
            this.service.put(userID,newUser);
            this.localeService.addUser(userID,newUser.getLocale().getId());
        }else{
            this.service.put(userID,newUser);
            newUser.setName(userID);
        }
        return profile(model,userID,session);
    }

    //TODO: implement login template as login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    //TODO: Implement login POST
    @PostMapping("/login")
    public RedirectView login(@RequestParam String name,@RequestParam String pass, HttpSession session){
        User user = service.get(name);
        if (user!=null && user.getPass().equals(pass)){
            session.setAttribute("user",user);
            return new RedirectView("/");
        }else{
            return new RedirectView("/login");
        }

    }
    @GetMapping("/logout")
    public RedirectView logout(HttpSession session){
        session.invalidate();
        return new RedirectView("/");
    }

    @GetMapping("/register")
    public String getRegister (){
        return "register";
    }
    @PostMapping("/register")
    public RedirectView register (User user, HttpSession session){
        this.service.post(user);
        session.setAttribute("user",user);  //Once registered, assign the new user to the session
        return new RedirectView("/");
    }
    //TODO: Implement 'logout' as redirect to home page
    @PostMapping("/deleteuser")
    @ResponseStatus(HttpStatus.OK)
    public RedirectView delete(HttpServletRequest request){
        if(request != null){
            String userID = request.getUserPrincipal().getName();
            this.service.delete(userID);
        }
        return new RedirectView("/");
    }
}

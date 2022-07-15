package es.dws.quidditch.controller;

import es.dws.quidditch.model.Game;
import es.dws.quidditch.model.Locale;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Controller
public class LocaleController {
    @Autowired
    private LocaleService service;
    @Autowired
    private UserService userService;

    //TODO: get user's balance
    @PostConstruct
    public void postConstruct(){
        this.userService.post(new User("user@user.com","user","12345678T",
                "pass",
                "USER"));

        this.userService.post(new User("admin@admin.com","admin","87654321T",
                "adminpass",
                "USER","ADMIN"));
        Locale locale = new Locale("Móstoles I","Calle Tulipán 24",0.25, "8:00","20:00");
        locale.setOwner(this.userService.get("admin"));
        User user =this.userService.get("user");
        locale.addUser(user);
        this.service.post(locale);
        this.service.addGame(1,1);
    }
    @GetMapping("/locale")
    public String showAllLocale (Model model){
        Collection<Locale> locales = this.service.get();
        model.addAttribute("locale",locales);
        return "section";
    }
    @GetMapping("/locale/{id}")
    public String showLocale (Model model, @PathVariable long id, HttpServletRequest request){
        Locale locale = this.service.get(id);
        List<Game> games = locale.getGames();
        if(request.getUserPrincipal()!=null && this.userService.get(request.getUserPrincipal().getName()).getLocale().getId() == id){
            User user = this.userService.get(request.getUserPrincipal().getName());
            model.addAttribute("amount",user.getBalance());
            model.addAttribute("self",true);
        }else{
            model.addAttribute("self",false);
        }
        model.addAttribute("game",games);
        model.addAttribute("locale",locale);

        return "locale";
    }
    @PostMapping("/edit_locale/add_user")
    @ResponseStatus(HttpStatus.OK)
    public String addUser(Model model, @RequestParam long localeid, @RequestParam String userid){
        this.service.addUser(userid, localeid);
        return "confirm";
    }
    @PostMapping("/edit_locale/add_match")
    @ResponseStatus(HttpStatus.OK)
    public String addGame(Model model,@RequestParam long localeid,@RequestParam long gameid){
        this.service.addGame(gameid,localeid);
        return "confirm";
    }

    //TODO: add 'delete' for every attribute (user,match,bet)

    @PostMapping("/edit_locale")
    public String editLocale(Model model,@RequestParam long localeID, Locale newLocale){
        this.service.put(localeID,newLocale);
        return "confirm";
    }

    @PostMapping("/delete_locale/user")
    @ResponseStatus(HttpStatus.OK)
    public String removeUser(Model model, long localeID, String userID){
        this.service.removeUser(localeID,userID);
        return "confirm";
    }

    @PostMapping("/delete_locale")
    @ResponseStatus(HttpStatus.OK)
    public String delete(Model model,long localeID){
        this.service.delete(localeID);
        return "confirm";
    }
}

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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/locale")
public class LocaleController {
    @Autowired
    private LocaleService service;
    @Autowired
    private UserService userService;

    //TODO: get user's balance
    @GetMapping("/locale/{id}")
    public String showLocale (Model model, @PathVariable long id, HttpServletRequest request){
        Locale locale = this.service.get(id);
        List<Game> games = locale.getGames();
        User user = this.userService.get(request.getUserPrincipal().getName());
        model.addAttribute("amount",user.getBalance());
        model.addAttribute("games",games);
        return "locale";
    }
    @GetMapping("/locale")
    public String showLocale (Model model){
        ArrayList<Locale> locales = this.service.get();
        //model.addAttribute("locales",locales);
        return "section";
    }
    @PostMapping("/edit_locale/add_user")
    @ResponseStatus(HttpStatus.OK)
    public String addUser(Model model, long localeID, User user){
        this.service.put(localeID,user);
        return "confirm";
    }
    @PostMapping("/edit_locale/add_match")
    @ResponseStatus(HttpStatus.OK)
    public String addMatch(Model model, long localeID, long matchID){
        this.service.put(localeID,matchID);
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

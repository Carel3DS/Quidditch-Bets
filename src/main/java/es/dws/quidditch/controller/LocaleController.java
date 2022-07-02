package es.dws.quidditch.controller;

import es.dws.quidditch.model.Locale;
import es.dws.quidditch.model.User;
import es.dws.quidditch.service.LocaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/locale")
public class LocaleController {
    @Autowired
    private LocaleService service;
    
    @GetMapping("/locales/{id}")
    public String showLocale (Model model, @PathVariable long id){
        Locale locale = this.service.get(id);
        return "locale";
    }
    @GetMapping("/locale")
    public String showLocale (Model model){
        ArrayList<Locale> locale = this.service.get();
        return "locale";
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
    public String editLocale(Model model, long localeID, Locale newLocale){
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

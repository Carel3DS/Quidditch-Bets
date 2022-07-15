package es.dws.quidditch.controller;

import es.dws.quidditch.model.Game;
import es.dws.quidditch.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;

@Controller
public class GameController {

    @Autowired
    private GameService service;

    @PostConstruct
    public void postConstruct(){
        Game game = new Game("Slytherin","Gryffindor","2022-05-12","20:00");
        service.post(game);
    }

    @PostMapping("/newGame")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(Game game){
        this.service.post(game);
        return "welcome";
    }
    @GetMapping("/games")
    public String show (Model model){
        Collection<Game> games = this.service.get();
        model.addAttribute("game",games);
        return "games";
    }
    @PostMapping("/edit_game")
    @ResponseStatus(HttpStatus.OK)
    public String edit(Model model,long gameID, Game newGame){
        this.service.put(gameID,newGame);
        return "confirm";
    }
    @PostMapping("/delete_game")
    @ResponseStatus(HttpStatus.OK)
    public String delete(Model model,long gameID){
        this.service.delete(gameID);
        return "confirm";
    }
}

package es.dws.quidditch.controller;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.repository.BetRepository;
import es.dws.quidditch.service.BetService;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BetController {


    @Autowired
    private BetService service;
    @Autowired
    private UserService userService;

    public void create(Bet bet, String userID){
        this.service.post(bet);
        this.userService.addBet(userID,bet);
    }
    public ResponseEntity<Bet> show (@PathVariable long id){
        Bet bet = this.service.get(id);
        if(bet != null){
            return new ResponseEntity<>(bet, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    public void edit(long betID, Bet newBet){
        this.service.put(betID,newBet);
    }
    public void delete(Bet bet){}
}

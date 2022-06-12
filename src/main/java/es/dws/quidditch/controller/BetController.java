package es.dws.quidditch.controller;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.repository.BetRepository;
import es.dws.quidditch.service.BetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class BetController {

    @Autowired
    private BetRepository repository;
    @Autowired
    private BetService service;

    public void create(Bet bet){
        repository.save(bet);
    }
    public ResponseEntity<Bet> show (@PathVariable long id){
        Optional<Bet> op = repository.findById(id);
        if(op.isPresent()){
            Bet bet = op.get();
            return new ResponseEntity<>(bet, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    public void edit(Bet bet, Bet newBet){}
    public void delete(Bet bet){}
}

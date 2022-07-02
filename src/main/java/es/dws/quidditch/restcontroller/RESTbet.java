package es.dws.quidditch.restcontroller;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.User;
import es.dws.quidditch.service.BetService;
import es.dws.quidditch.service.GameService;
import es.dws.quidditch.service.LocaleService;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RequestMapping("/api")
@RestController
public class RESTbet {
    @Autowired
    private BetService betService;
    @Autowired
    private GameService gameService;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private UserService userService;

    @PostMapping("/bet")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Bet> postBet(@RequestBody Bet bet, @RequestParam String name, @RequestParam long gameID){
        if(this.gameService.exists(gameID) && this.userService.exists(name) && this.localeService.hasGame(name,gameID)){
            User user = this.userService.get(name);
            double balance = user.getBalance();
            if(balance>=bet.getAmount()){
                bet.setGame(this.gameService.get(gameID));
                this.betService.post(user,bet);
                return new ResponseEntity<>(HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/bet")
    public ResponseEntity<Bet> getBet(@RequestParam long id){
        Bet bet = this.betService.get(id);
        if(bet!=null) {
            return new  ResponseEntity<>(bet, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/bet")
    public ResponseEntity<Bet> putBet(@RequestParam long id, @RequestBody Bet bet){
        if(this.betService.get(id) != null){
            this.betService.put(id,bet);
            return new ResponseEntity<>(bet, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @DeleteMapping("/bet")
    public ResponseEntity<Bet> deleteBet(@RequestParam long id){
        Bet bet = this.betService.get(id);
        if(bet != null){
            this.betService.delete(id);
            return new ResponseEntity<>(bet, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

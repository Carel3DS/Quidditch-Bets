package es.dws.quidditch.service;

import es.dws.quidditch.model.*;
import es.dws.quidditch.repository.BetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class BetService {
    private EntityManager entityManager;
    @Autowired
    BetRepository betRepository;
    @Autowired
    GameService gameService;

    //API REST service (ID = gameID+userID)

    //Charges the balance with bet's amount. Supposes it can be retained
    public User post(User user, Bet bet){
        betRepository.save(bet);
        user.setBalance(user.getBalance()-bet.getAmount());
        user.addBet(this.betRepository.getTopByOrderByIdDesc());
        return  user;
    }
    public Bet get(User user, Game game){
        return this.betRepository.findByUserAndGame(user,game).orElse(null);
    }
    public Bet get(long id){
        return this.betRepository.findById(id).orElse(null);
    }
    public List<Bet> getByGame(long gameID){
        return this.betRepository.findAllByGame(this.gameService.get(gameID));
    }
    public void put(long id, Bet newBet){
        newBet.setId(id);
        this.betRepository.save(newBet);
    }
    public void delete(long id){
        this.betRepository.deleteById(id);
    }
    //In this case, the balance is not refunded
    public void delete(User user){
        this.betRepository.deleteByUser(user);
    }
    //Refunds the money to users if the locale closes
    public void delete(Locale locale){
        this.betRepository.deleteByLocale(locale);
    }
    
    ///////////////////////////
    // SPECIFIC BET SERVICES //
    ///////////////////////////
    public void cancel(long gameID){//If a game gets cancelled, all the bets related get destroyed
        ArrayList<Bet> bets = (ArrayList<Bet>) this.gameService.get(gameID).getBets();
        for (Bet bet: bets) {
            this.delete(bet.getId());
        }
    }
    public void update(long gameID){
    Game game = this.gameService.get(gameID);
    if (game != null){
        if (game.getStatus().equals(Status.FINISHED)){
            int result = game.getResult();
            ArrayList<Bet> bets = this.betRepository.findAllByGame(game);
            for (Bet bet:bets){
                bet.setResult(result);
                this.betRepository.save(bet);
            }
        } else if (game.getStatus().equals(Status.CANCELLED)) {
            this.cancel(gameID);
        }
    }
    } //Updates users' balance with the results of a bet once the game finishes or gets cancelled

    public boolean exists(long id) {
        return this.betRepository.findById(id).isPresent();
    }
}

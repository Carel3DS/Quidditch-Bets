package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.Status;
import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.BetRepository;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class BetService {
    private EntityManager entityManager;
    @Autowired
    BetRepository betRepository;
    @Autowired
    MatchService matchService;
    @Autowired
    UserService userService;
    @Autowired
    UserRepository users;
    //API REST service (ID = BetID+User)
    public void post(Bet bet){
        betRepository.save(bet);
    }
    public Bet get(long betID){
        Optional<Bet> op = betRepository.findById(betID);
        return op.orElse(null);
    }
    public void put(long betID, Bet newBet){
        newBet.setId(betID);
        betRepository.save(newBet);
    }
    public void delete(long betID){
        betRepository.deleteById(betID);
    }
    
    
    ///////////////////////////
    // SPECIFIC BET SERVICES //
    ///////////////////////////
    public void cancel(long matchID){//If a match gets cancelled, all the bets related get destroyed
        ArrayList<Bet> bets = this.matchService.get(matchID).getBets();
        for (Bet bet: bets) {
            this.delete(bet.getId());
        }
    }
    public void update(long matchID){
    Match match = this.matchService.get(matchID);
    if (match != null){
        if (match.getStatus().equals(Status.FINISHED)){
            int result = match.getResult();
            ArrayList<Bet> bets = this.betRepository.findAllByMatch(match);
            for (Bet bet:bets){
                bet.setResult(result);
                betRepository.save(bet);
            }
            this.userService.updateBalance(bets,matchID);
        } else if (match.getStatus().equals(Status.CANCELLED)) {

        }
    }
    } //Updates users' balance with the results of a bet once the match finishes or gets cancelled

}

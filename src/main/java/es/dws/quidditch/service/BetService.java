package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.Game;
import es.dws.quidditch.model.Status;
import es.dws.quidditch.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//not persistent
@Service
public class BetService {
    @Autowired
    GameService gameService;

    //Not persistent
    private Map<Long, Bet> bets= new ConcurrentHashMap<>();
    private AtomicLong lastID= new AtomicLong();

    public void post(Bet bet){
        long id = lastID.incrementAndGet();
        Game game = bet.getGame();
        bet.setId(id);
        game.addBet(bet);
        bets.put(id,bet);
        gameService.put(game.getId(),game);
    }


    public Collection<Bet> get(){
        return bets.values();
    }

    public Bet get(long id){
        return bets.get(id);
    }

    public void put(long id, Bet bet){
        bets.put(id,bet);
    }

    public Bet delete(long id){
        Bet bet = bets.get(id);
        if(bet !=null){
            bets.remove(id);
            Game game = bet.getGame();
            game.removeBet(bet);
            this.gameService.put(game.getId(), game);
        }
        return bet;
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
                for (Bet bet:bets.values()){
                    if(bet.getGame().equals(game)){
                        bet.setResult(result);
                        this.bets.put(bet.getId(), bet);
                    }
                }
            } else if (game.getStatus().equals(Status.CANCELLED)) {
                this.cancel(gameID);
            }
        }
    } //Updates users' balance with the results of a bet once the game finishes or gets cancelled

    public boolean exists(long id) {
        return this.bets.containsKey(id);
    }
}

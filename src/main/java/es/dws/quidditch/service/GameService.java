package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.Game;
import es.dws.quidditch.model.Status;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//not persistent
@Service
public class GameService {

    private Map<Long, Game> games= new ConcurrentHashMap<>();
    private AtomicLong lastID= new AtomicLong();

    public void post(Game game){
        long id = lastID.incrementAndGet();
        game.setId(id);
        games.put(id,game);
    }


    public Collection<Game> get(){
        return games.values();
    }

    public Game get(long id){
        return games.get(id);
    }

    public void put(long id, Game game){
        games.put(id,game);
    }

    public Game delete(long id){
        Game game = games.get(id);
        if(game !=null){
            games.remove(id);
        }
        return game;
    }
    ///////////////////////////
    // SPECIFIC GAME SERVICES //
    ///////////////////////////
    public boolean exists(long gameID) {
        return this.games.containsKey(gameID);
    }

    public void cancel(long gameID){
        Game game = this.get(gameID);
        if(game != null){
            game.setStatus(Status.CANCELLED);
            games.put(gameID,game);
        }

    } //The game is set as "Cancelled"

    public void update(long gameID, int r){
        Game game = this.games.get(gameID);
        if(game != null){
            game.setStatus(game.getStatus().next());
            if (game.getStatus().equals(Status.FINISHED)){
                game.setResult(r);
            }
            this.games.put(gameID, game);
        }
    } //Updates the game with its results/status of the game.

    public double[] stats(long gameID){
        int x = 0, y = 0, e=0;
        Game m = this.get(gameID);    //we suppose the gameID is valid
        for (Bet bet:m.getBets()) {
            switch (bet.getPrediction()){
                case(0) -> e++;
                case(1) -> x++;
                case(2) -> y++;
            }
        }
        int total= x+y+e;
        return new double[] {(double)(total)/x,(double)(total)/e,(double)(total)/y};
    }
}

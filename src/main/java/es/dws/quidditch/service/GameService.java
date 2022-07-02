package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.Game;
import es.dws.quidditch.model.Status;
import es.dws.quidditch.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    GameRepository gameRepository;

    //API REST service
    public void post(Game game){
        gameRepository.save(game);
    }

    //Get all gamees
    public ArrayList<Game> get(){
        return new ArrayList<>(this.gameRepository.findAll());
    }
    //Get a specific game
    public Game get(long gameID){
        Optional<Game> op = gameRepository.findById(gameID);
        return op.orElse(null);
    }
    public void put(long gameID, Game newGame){
            newGame.setId(gameID);
            gameRepository.save(newGame);
    }
    public Game delete(long gameID){
        Game game = gameRepository.findById(gameID).orElse(null);
        if (game != null){
            game.getBets().size();
            gameRepository.deleteById(gameID);
        }
        return game;
    }
    ///////////////////////////
    // SPECIFIC GAME SERVICES //
    ///////////////////////////
    public void cancel(long gameID){
        Game game = this.get(gameID);
        if(game != null){
            game.setStatus(Status.CANCELLED);
            gameRepository.save(game);
        }

    } //The game sets itself as "Cancelled" and destroys all the bets.

    public void update(long gameID, int r){
        Game game = this.gameRepository.findById(gameID).orElse(null);
        if(game != null){
            game.setStatus(game.getStatus().next());
            if (game.getStatus().equals(Status.FINISHED)){
                game.setResult(r);
            }
            this.gameRepository.save(game);
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

    public boolean exists(long gameID) {
        return this.gameRepository.findById(gameID).isPresent();
    }
}

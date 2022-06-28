package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.Status;
import es.dws.quidditch.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    BetService betService;
    @Autowired
    MatchRepository matchRepository;

    //API REST service
    public void post(Match match){
        matchRepository.save(match);
    }
    public Match get(long matchID){
        Optional<Match> op = matchRepository.findById(matchID);
        return op.orElse(null);
    }
    public void put(long matchID, Match newMatch){
            newMatch.setId(matchID);
            matchRepository.save(newMatch);
    }
    public Match delete(long matchID){
        Match match = matchRepository.findById(matchID).orElse(null);
        if (match != null){
            match.getBets().size();
            matchRepository.deleteById(matchID);
        }
        return match;
    }
    ///////////////////////////
    // SPECIFIC MATCH SERVICES //
    ///////////////////////////
    public void cancel(long matchID){
        Match match = this.get(matchID);
        if(match != null){
            betService.cancel(matchID);
            match.setStatus(Status.CANCELLED);
            matchRepository.save(match);
        }

    } //The match sets itself as "Cancelled" and destroys all the bets.
    public void update(Match match, Match newMatch){
        newMatch.setId(newMatch.getId());
        matchRepository.save(match);
    } //Updates the match with its results/status of the game.

    public double[] stats(long matchID){
        int x = 0, y = 0, e=0;
        Match m = this.get(matchID);    //we suppose the matchID is valid
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

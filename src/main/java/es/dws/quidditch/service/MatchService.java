package es.dws.quidditch.service;

import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.User;
import org.springframework.stereotype.Service;

@Service
public class MatchService {
    //API REST service (ID = MatchID+User)
    public void post(Match match, User user){}
    public void get(long matchID, User user){}
    public void put(long matchID, User user/*, smth else here*/){}
    public void delete(long matchID, User user){}
    ///////////////////////////
    // SPECIFIC MATCH SERVICES //
    ///////////////////////////
    public void cancel(Match match){} //The match destroys itself and locales destroy it too
    public void update(Match match){} //Updates the match with its results/status of the game.
}

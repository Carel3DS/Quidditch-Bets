package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.BetRepository;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BetService {
    @Autowired
    BetRepository bets;
    @Autowired
    UserRepository users;
    //API REST service (ID = BetID+User)
    public void post(Bet bet, User user){}
    public void get(long betID, User user){}
    public void put(long betID, User user/*, smth else here*/){}
    public void delete(long betID, User user){}
    ///////////////////////////
    // SPECIFIC BET SERVICES //
    ///////////////////////////
    public void cancel(Match match){} //If a match gets cancelled, all bets are destroyed
    public void update(Match match){} //Updates bets with the results of a match.

}

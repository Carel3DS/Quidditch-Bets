package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.User;
import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    //API REST service (ID = UserID)
    public void post(User user){}
    public void get(long userID){}
    public void put(long userID){}
    public void delete(long userID){}
    ///////////////////////////
    // SPECIFIC USER SERVICES //
    ///////////////////////////
    public void create(Bet bet){} //Creates the user and assigns to the user
}

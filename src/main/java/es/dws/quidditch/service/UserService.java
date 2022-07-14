package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

//not persistent
@Service
public class UserService {
    @Autowired
    private BetService betService;
    
    //Not persistent
    private Map<String, User> users= new ConcurrentHashMap<>();

    public void post(User user){
        users.put(user.getName(),user);
    }

    public void put(String id, User user){
        users.put(id,user);
    }

    public Collection<User> get(){
        return users.values();
    }

    public User get(String id){
        return users.get(id);
    }

    public User delete(String id){
        User user = users.get(id);
        Collection<Bet> bets = user.getBets();
        if(!bets.isEmpty()){
            for (Bet bet: bets){
                betService.delete(bet.getId());
            }
        }
        users.remove(id);
        return user;
    }
    ////////////////////////////
    // SPECIFIC USER SERVICES //
    ////////////////////////////

    public void add(String userID, Bet bet){
        User user = this.users.get(userID);
        user.addBet(bet);
        user.setBalance(user.getBalance()-bet.getAmount());
        users.put(userID,user);
    } //Assigns a new bet to a user

    //TODO: Set control when removing a bet in user controller
    public void remove(String userID, Bet bet){
        User user = this.get(userID);
        user.setBalance(user.getBalance()+bet.getAmount());
        user.removeBet(bet);
        this.betService.delete(bet.getId());
        users.put(userID,user);
    }//Deletes bet given user and betID

    public boolean exists(String userID) {
        return this.users.containsKey(userID);
    }

    //removes all the bets and refunds users money
    //TODO: apply locale fees
    public void refund(String userID) {
        User user = users.get(userID);
        for(Bet bet:user.getBets()) {
            user.setBalance(user.getBalance()+bet.getAmount());
            user.removeBet(bet);
        }
        users.put(userID,user);

    }
}

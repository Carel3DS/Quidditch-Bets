package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.BetRepository;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BetService betService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    //API REST service (ID = UserID)
    public void post(User user){
        userRepository.save(user);
    }
    public User get(String userID){
        Optional<User> op = userRepository.findByName(userID);
        return op.orElse(null);
    }
    public void put(String userID, User newUser){
        newUser.setName(userID);
        userRepository.save(newUser);
    }
    public User delete(String userID){
        User user = userRepository.findByName(userID).orElse(null);
        if (user!=null){
            user.getBets().size();
            userRepository.deleteUserByName(userID);
        }
        return user;
    }
    ////////////////////////////
    // SPECIFIC USER SERVICES //
    ////////////////////////////

    public void addBet(String userID, Bet bet){
        User user = this.userRepository.findByName(userID).orElse(null);
        if(user != null){
            user.addBet(bet);
            userRepository.save(user);
        }
    } //Assigns a new bet to a user
    public void remove(String userID,long betID){
        User user = this.get(userID);
        Bet bet = this.betService.get(betID);
        if (user.getBets().contains(bet)){
            user.setBalance(user.getBalance()+bet.getAmount());
        }
    }

    //If the user creates a bet or something else, update the balance
    public void updateBalance(String userID, double balance){
        User user = this.userRepository.findByName(userID).orElse(null);
        if (user != null){
            user.setBalance(balance);
            this.userRepository.save(user);
        }
    }

    //If match has finished, update the balance of the user who have any of these bets betting in that match
    public void updateBalance(ArrayList<Bet> bets, long matchID) {
        ArrayList<User> users = userRepository.findAllByBetsContaining(bets);
        double [] stats = this.matchService.stats(matchID);
        for (User user:users){
            double balance = user.getBalance();
            ArrayList<Bet> userBets = user.getBets();
            for (Bet bet:userBets){
                if (bets.contains(bet)){
                    if(bet.hasWon()){
                        user.setBalance(balance+bet.getAmount()*stats[bet.getPrediction()]);
                        this.userRepository.save(user);
                    }
                    bets.remove(bet);
                }
            }
        }
    }
}

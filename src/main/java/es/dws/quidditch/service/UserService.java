package es.dws.quidditch.service;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private BetService betService;
    @Autowired
    private GameService matchService;
    @Autowired
    private UserRepository userRepository;

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
            betService.delete(user);
            user.getBets().size();
            userRepository.deleteByName(userID);
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
            user.setBalance(user.getBalance()-bet.getAmount());
            userRepository.save(user);
        }
    } //Assigns a new bet to a user
    //TODO: Set control when removing a bet in user controller
    public void remove(String userID, Bet bet){
        User user = this.get(userID);
        user.setBalance(user.getBalance()+bet.getAmount());
        user.removeBet(bet);
        this.betService.delete(bet.getId());
    }//Deletes bet given user and betID

    public boolean exists(String userID) {
        return this.userRepository.findByName(userID).isPresent();
    }

    //removes all the bets and refunds users money
    public void refund(String userID) {
        User user = this.get(userID);
        for(Bet bet:user.getBets()) {
            this.remove(userID,bet);
        }


    }
}

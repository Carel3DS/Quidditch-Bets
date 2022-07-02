package es.dws.quidditch.service;

import es.dws.quidditch.model.*;
import es.dws.quidditch.repository.LocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LocaleService {

    @Autowired
    LocaleRepository localeRepository;
    @Autowired
    BetService betService;
    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;
    //API REST service (ID = LocaleID+User)
    public void post(Locale locale, User user){
        user.setLocale(locale);
        this.localeRepository.save(locale);
    }
    public ArrayList<Locale> get(){
        return new ArrayList<>(this.localeRepository.findAll());
    }
    public Locale get (long localeID){
        Optional<Locale> op = localeRepository.findById(localeID);
        return op.orElse(null);
    }

    public Locale get (User user){
        return user.getLocale();
    }
    
    //Adds a new user
    public void put(long localeID, User user){
       Locale locale=this.localeRepository.findById(localeID).orElse(null);
       if(locale != null){
            locale.addUser(user);
            this.localeRepository.save(locale);
       }
    }
    //Adds a new game
    public void put(long localeID,long gameID){
        Locale locale=this.localeRepository.findById(localeID).orElse(null);
        Game game = this.gameService.get(gameID);
        if(locale != null && game != null){
            locale.addGame(game);
            this.localeRepository.save(locale);
        }
    }
    //edits the locale
    public void put(long localeID,Locale newLocale){
        Locale locale = this.localeRepository.findById(localeID).orElse(null);
        if (locale != null){
            newLocale.setUsers(locale.getUsers());
            newLocale.setBets(locale.getBets());
            newLocale.setGames(locale.getGames());
            newLocale.setId(localeID);
            this.localeRepository.save(newLocale);
        }
    }
    
    //Deletes a user
    public void removeUser(long localeID, String userID){
        User user = this.userService.get(userID);
        Locale locale = this.get(localeID);
        this.betService.delete(user);
        user.setBets(null);
        user.setLocale(null);
        locale.removeUser(user);
        this.userService.put(user.getName(),user);
    }
    //Deletes the locale
    public Locale delete(long localeID){
        Locale locale = this.localeRepository.findById(localeID).orElse(null);
        if (locale != null){
            for (User user:locale.getUsers()){
                user.setLocale(null);
                this.userService.put(user.getName(),user);
                this.userService.refund(user.getName());
            }
            localeRepository.deleteById(localeID);
        }
        return locale;
    }
    //////////////////////////////
    // SPECIFIC LOCALE SERVICES //
    //////////////////////////////
    public void update(long gameID, int r){
        this.gameService.update(gameID,r);
        this.betService.update(gameID);
        Game game = this.gameService.get(gameID);
        if(game.getStatus().equals(Status.FINISHED)){
            double [] stats = this.gameService.stats(gameID);
            for(Bet bet:this.betService.getByGame(gameID)){
                User user = bet.getUser();
                if(bet.hasWon()){
                    user.setBalance(user.getBalance()+bet.getAmount()*stats[bet.getPrediction()]);
                }
                user.removeBet(bet);
                userService.put(user.getName(),user);
            }

        }
    }
    public void cancel(Game game, long localeID){
        Locale locale = this.localeRepository.findById(localeID).orElse(null);
        if (locale != null && locale.hasGame(game)){
            locale.removeGame(game);
            locale.getGames().size();
            this.localeRepository.save(locale);
        }
    } //locale removes the game

    public boolean hasGame(String userID, long gameID) {
        User user = this.userService.get(userID);
        if(user != null){
            ArrayList<Game> games = (ArrayList<Game>)user.getLocale().getGames();
            Game game = this.gameService.get(gameID);
            return games.contains(game);
        }else{
            return false;
        }
    }
}

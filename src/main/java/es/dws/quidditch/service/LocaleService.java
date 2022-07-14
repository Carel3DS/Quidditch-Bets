package es.dws.quidditch.service;

import es.dws.quidditch.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

//not persistent
@Service
public class LocaleService {
    @Autowired
    BetService betService;
    @Autowired
    UserService userService;
    @Autowired
    GameService gameService;

    //Not persistent
    private Map<Long, Locale> locales= new ConcurrentHashMap<>();
    private AtomicLong lastID= new AtomicLong();

    public void post(Locale locale){
        long id = lastID.incrementAndGet();
        locale.setId(id);
        locales.put(id,locale);
    }


    public Collection<Locale> getLocales(){
        return locales.values();
    }

    public Locale get(long id){
        return locales.get(id);
    }

    public void put(long id, Locale locale){
        Locale oldLocale = this.get(id);
        locale.setUsers(oldLocale.getUsers());
        locale.setBets(oldLocale.getBets());
        locale.setGames(oldLocale.getGames());
        locales.put(id,locale);
    }

    public Locale delete(long id){
        Locale locale = locales.get(id);
        for(User user: locale.getUsers()){
            userService.refund(user.getName());
            user.setLocale(null);
        }
        locales.remove(id);
        return locale;
    }
    //////////////////////////////
    // SPECIFIC LOCALE SERVICES //
    //////////////////////////////
    
    public void addGame(long id, long localeId){
        Game game = gameService.get(id);
        Locale locale = get(localeId);
        locale.addGame(game);
        locales.put(localeId,locale);
    }
    public void addUser(String id, long localeId){
        User user = userService.get(id);
        Locale locale = get(localeId);
        locale.addUser(user);
        locales.put(localeId,locale);
    }
    public void addBet(long id, long localeId){
        Bet bet = betService.get(id);
        Locale locale = get(localeId);
        locale.addBet(bet);
        locales.put(localeId,locale);
    }
    public void update(long gameID, int r){

        this.gameService.update(gameID,r);
        this.betService.update(gameID);

        Game game = this.gameService.get(gameID);
        if(game.getStatus().equals(Status.FINISHED)){

            double [] stats = this.gameService.stats(gameID);
            for(Bet bet:this.betService.get()){

                if(bet.getGame().equals(game) && bet.hasWon()){
                    User user = bet.getUser();
                    user.setBalance(user.getBalance()+bet.getAmount()*stats[bet.getPrediction()]);
                    user.removeBet(bet);
                    userService.put(user.getName(),user);
                }

            }

        }
    }
    public void cancel(Game game, long localeID){
        Locale locale = this.locales.get(localeID);
        if (locale != null && locale.hasGame(game)){
            locale.removeGame(game);
            locale.getGames().size();
            this.locales.put(localeID,locale);
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

    public boolean exists(long id) {
        return locales.containsKey(id);
    }

    public void removeUser(long id, String userId) {
        Locale locale = this.locales.get(id);
        User user = this.userService.get(userId);
        if(locale.getUsers().contains(user)){
            locale.removeUser(user);
        }
    }

    public Collection<Locale> get() {
        return this.locales.values();
    }
}

package es.dws.quidditch.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
public class Locale {
    //ATTRIBUTES

    private long id;            //Primary Key

    private String name;

    private String address;     //Not null

    private LocalTime open;

    private LocalTime close;
    private double fee = 0;     //DEFAULT: 0

    //RELATIONSHIP
    private List<Game> games = new ArrayList<>();
    private List<Bet> bets = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private User owner;
    public Locale(String name, String address, double fee, String open, String close) {
        this.name = name;
        this.address = address;
        this.fee = fee;
        this.open = LocalTime.parse(open,
                DateTimeFormatter.ofPattern("H:mm"));
        this.close = LocalTime.parse(close,
                DateTimeFormatter.ofPattern("H:mm"));
    }
    public void addGame(Game game){
        this.games.add(game);
    }
    public boolean hasGame(Game game){
        return this.games.contains(game);
    }
    public void removeGame(Game game){
        this.games.remove(game);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public void addBet(Bet bet) {
        this.bets.add(bet);
    }
}

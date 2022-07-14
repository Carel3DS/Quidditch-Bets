package es.dws.quidditch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor

public class User {
    //ATTRIBUTES

    private String name;              //Primary key
    private String email;             //Unique
    private String dni;              //Unique
    private String pass;             //Not null
    private String team;
    private String description;
    private double balance = 0;     //Not null. DEFAULT = 0, CHECK(balance>=0)




    //Stats
    private double win;
    private double lose;
    private double earnings;
    private double losses;

    //RELATIONSHIP
    private List<Bet> bets;
    private Locale locale;



    public User(String email, String name, String dni, String pass, String... roles) {
        this.email = email;
        this.name = name;
        this.dni = dni;
        this.pass = pass;
        this.balance = 0;
    }

    public void addBet(Bet bet){
        this.bets.add(bet);
    }
    public void removeBet(Bet bet){ this.bets.remove(bet);}
    public double performance(){ return this.win/(this.win+this.lose);}
    public double totalEarnings(){return this.earnings-this.losses;}

    //TODO: fix 'user.hasBet()' method
    public boolean hasBet(Game game) {
        boolean res = false;
        int i = 0;
        while(!res){
            Bet bet = bets.get(i);
            res = bet.getGame().equals(game);
        }
        return res;
    }
}

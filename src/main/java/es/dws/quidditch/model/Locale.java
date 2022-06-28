package es.dws.quidditch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Locale {
    //ATTRIBUTES
    @Id
    private String name;        //Primary Key
    private String address;     //Not null
    private double fee = 0;     //DEFAULT: 0

    //RELATIONSHIP
    @ManyToMany
    private ArrayList<Match> matches;
    @OneToMany
    private ArrayList<Bet> bets;
    @OneToMany
    private ArrayList<User> users;


    public Locale(String name, String address, double fee) {
        this.name = name;
        this.address = address;
        this.fee = fee;
    }
    public void addMatch(Match match){
        this.matches.add(match);
    }
    public boolean hasMatch(Match match){
        return this.matches.contains(match);
    }
    public void removeMatch(Match match){
        this.matches.remove(match);
    }
}

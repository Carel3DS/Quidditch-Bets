package es.dws.quidditch.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
public class Locale {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String name;        //Primary Key
    private String address;     //Not null
    private double fee = 0;     //DEFAULT: 0

    //RELATIONSHIP
    @OneToMany
    private ArrayList<Bet> bets;
    @OneToMany
    private ArrayList<User> users;


    public Locale(String name, String address, double fee) {
        this.name = name;
        this.address = address;
        this.fee = fee;
    }
}

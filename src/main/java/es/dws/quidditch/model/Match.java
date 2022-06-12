package es.dws.quidditch.model;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Match {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;            //Primary Key
    private String team1;       //Not null
    private String team2;       //Not null, team2<>team1
    private double multiplier;  //Not null
    private Status status;
    private int result;

    //RELATIONSHIP
    @ManyToMany(mappedBy = "locale")
    private ArrayList<Bet> bet;
}

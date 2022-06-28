package es.dws.quidditch.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;

@Entity
@Getter
@Setter
public class Match {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;            //Primary Key
    private String team1;       //Not null
    private String team2;       //Not null, team2<>team1
    private double multiplier;  //Not null
    private Date date;          //Not null
    private Status status;      //Not null
    private int result;         //Not null

    //RELATIONSHIP
    @OneToMany(mappedBy = "match")
    private ArrayList<Bet> bets;
}

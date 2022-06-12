package es.dws.quidditch.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@NoArgsConstructor
@AllArgsConstructor

public class Bet {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;        //Primary Key
    private double amount;  //Not null

    //RELATIONSHIP
    @ManyToMany
    private ArrayList<Match> matches;   //Not null
    @ManyToOne
    private Locale locale;              //Not null
}

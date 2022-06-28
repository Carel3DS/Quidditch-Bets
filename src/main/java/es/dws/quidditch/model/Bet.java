package es.dws.quidditch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;            //Primary Key
    private double amount;      //Not null
    private int prediction;     //Not null
    private int result;

    //RELATIONSHIP
    @ManyToOne
    private Match match;        //Not null
    @ManyToOne
    private Locale locale;      //Not null

    public boolean hasWon(){
        return prediction == result;
    }
}
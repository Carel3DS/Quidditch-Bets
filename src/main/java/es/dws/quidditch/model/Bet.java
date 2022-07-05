package es.dws.quidditch.model;

import lombok.*;

import javax.persistence.*;


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
    private User user;
    @ManyToOne
    private Game game;          //Not null
    @ManyToOne
    private Locale locale;      //Not null


    public boolean hasWon(){
        return prediction == result;
    }
}

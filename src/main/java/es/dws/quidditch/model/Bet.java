package es.dws.quidditch.model;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bet {
    //ATTRIBUTES
    private long id;            //Primary Key
    private double amount;      //Not null
    private int prediction;     //Not null
    private int result;

    //RELATIONSHIP
    private User user;
    private Game game;
    private Locale locale;


    public boolean hasWon(){
        return prediction == result;
    }
}

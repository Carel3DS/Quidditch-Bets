package es.dws.quidditch.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;



@Getter
@Setter
@NoArgsConstructor
public class Game {
    //ATTRIBUTES

    private long id;            //Primary Key
    private String team1;       //Not null)
    private String team2;       //Not null
    private LocalDate date;     //Not null
    private LocalTime time;     //Not null
    private Status status;      //Not null
    private int result;         //Not null

    //RELATIONSHIP
    private List<Bet> bets;

    public Game(String team1, String team2, String date,String time) {
        this.team1 = team1;
        this.team2 = team2;
        this.date = LocalDate.parse(date,
                DateTimeFormatter.ISO_LOCAL_DATE);
        this.time = LocalTime.parse(time,
                DateTimeFormatter.ISO_LOCAL_TIME);
        this.status = Status.OPEN;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return id == game.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    public void addBet(Bet bet) {
        this.bets.add(bet);
    }
    public void removeBet(Bet bet){
        this.bets.remove((int)bet.getId());
    }
}

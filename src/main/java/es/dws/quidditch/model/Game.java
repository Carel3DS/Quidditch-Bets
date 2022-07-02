package es.dws.quidditch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "game")
public class Game {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;            //Primary Key
    private String team1;       //Not null
    private String team2;       //Not null, team2<>team1
    private LocalDate date;     //Not null
    private Status status;      //Not null
    private int result;         //Not null

    //RELATIONSHIP
    @OneToMany(mappedBy = "game")
    private List<Bet> bets;

    public Game(String team1, String team2, String date) {
        this.team1 = team1;
        this.team2 = team2;
        this.date = LocalDate.parse(date,
                DateTimeFormatter.ISO_LOCAL_DATE);
        this.status = Status.OPEN;
    }
}

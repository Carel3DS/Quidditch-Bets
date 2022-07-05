package es.dws.quidditch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Game {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;            //Primary Key
    @Column(nullable = false)
    private String team1;       //Not null
    @Column(nullable = false)
    private String team2;       //Not null
    @Column(nullable = false)
    private LocalDate date;     //Not null
    @Column(nullable = false)
    private LocalTime time;     //Not null
    @Column(nullable = false)
    private Status status;      //Not null
    @Column(nullable = false)
    private int result;         //Not null

    //RELATIONSHIP
    @OneToMany(mappedBy = "game")
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
}

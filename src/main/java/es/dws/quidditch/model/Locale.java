package es.dws.quidditch.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
public class Locale {
    //ATTRIBUTES
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;            //Primary Key
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;     //Not null
    @Column(nullable = false)
    private LocalTime open;
    @Column(nullable = false)
    private LocalTime close;
    private double fee = 0;     //DEFAULT: 0

    //RELATIONSHIP
    @ManyToMany
    private List<Game> games = new ArrayList<>();
    @OneToMany
    private List<Bet> bets = new ArrayList<>();
    @OneToMany
    private List<User> users = new ArrayList<>();
    @OneToOne
    private User owner;
    public Locale(String name, String address, double fee, String open, String close) {
        this.name = name;
        this.address = address;
        this.fee = fee;
        this.open = LocalTime.parse(open,
                DateTimeFormatter.ofPattern("H:mm"));
        this.close = LocalTime.parse(close,
                DateTimeFormatter.ofPattern("H:mm"));
    }
    public void addGame(Game game){
        this.games.add(game);
    }
    public boolean hasGame(Game game){
        return this.games.contains(game);
    }
    public void removeGame(Game game){
        this.games.remove(game);
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }
}

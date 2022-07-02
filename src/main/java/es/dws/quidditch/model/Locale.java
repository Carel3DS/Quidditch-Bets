package es.dws.quidditch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
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
    private double fee = 0;     //DEFAULT: 0

    //RELATIONSHIP
    @ManyToMany
    private List<Game> games;
    @OneToMany
    private List<Bet> bets;
    @OneToMany
    private List<User> users;
    @OneToOne
    private User owner;
    public Locale(String name, String address, double fee) {
        this.name = name;
        this.address = address;
        this.fee = fee;
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

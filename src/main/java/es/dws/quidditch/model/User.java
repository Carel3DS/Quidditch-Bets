package es.dws.quidditch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "user")
public class User {
    //ATTRIBUTES
    @Id
    private String name;              //Primary key
    @Column(nullable = false,unique = true)
    private String email;             //Unique
    @Column(nullable = false,unique = true)
    private String dni;              //Unique
    @Column(nullable = false)
    private String pass;             //Not null
    private String team;
    private String description;
    private double balance = 0;     //Not null. DEFAULT = 0, CHECK(balance>=0)

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles;


    //RELATIONSHIP
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bet> bets;
    @ManyToOne
    private Locale locale;



    public User(String email, String name, String dni, String pass, List<String> roles) {
        this.email = email;
        this.name = name;
        this.dni = dni;
        this.pass = pass;
        this.balance = 0;
        this.roles = roles;
    }

    public User(String user, String pass, List<String> roles) {
        this.name = user;
        this.pass = pass;
        this.roles = roles;//We have only one kind of role
    }

    public void addBet(Bet bet){
        this.bets.add(bet);
    }
    public void removeBet(Bet bet){ this.bets.remove(bet);}
    public void removeBet(ArrayList<Bet> bets){
        for (Bet bet:bets){
            this.bets.remove(bet);
        }
    }

}

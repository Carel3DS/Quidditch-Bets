package es.dws.quidditch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {
    //ATTRIBUTES
    @Id
    private String email;   //Primary key
    private String name;    //Unique
    private String dni;     //Unique
    private String pass;    //Not null
    private String team;
    private boolean isAdmin;    //Not null. DEFAULT = False

    //RELATIONSHIP
    @OneToMany
    private ArrayList<Bet> bets;
    @ManyToOne
    private Locale locale;  //Not null
    @OneToOne
    private Locale owner;   //Not null if isAdmin = True

    public User(String email, String name, String dni, String pass) {
        this.email = email;
        this.name = name;
        this.dni = dni;
        this.pass = pass;
    }
}

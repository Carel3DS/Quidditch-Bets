package es.dws.quidditch.repository;

import es.dws.quidditch.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;


public interface BetRepository extends JpaRepository<Bet, Long> {

    Optional<Bet> findByUserAndGame(User user, Game game);


    void deleteById(long id);

    Bet getTopByOrderByIdDesc();
    @Override
    ArrayList<Bet> findAll();

    ArrayList<Bet> findAllByGame(Game game);

    void deleteByUser(User user);

    void deleteByLocale(Locale locale);

    ArrayList<Bet> findAllByLocale(Locale locale);

}

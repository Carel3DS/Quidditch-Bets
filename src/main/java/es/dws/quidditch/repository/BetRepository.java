package es.dws.quidditch.repository;

import es.dws.quidditch.model.Bet;
import es.dws.quidditch.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;


public interface BetRepository extends JpaRepository<Bet,Long> {
    @Override
    Optional<Bet> findById(Long betID);

    @Override
    void deleteById(Long betID);

    ArrayList<Bet> findAllByMatch(Match match);
}

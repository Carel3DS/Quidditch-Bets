package es.dws.quidditch.repository;

import es.dws.quidditch.model.Bet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BetRepository extends JpaRepository<Bet,Long> {
}

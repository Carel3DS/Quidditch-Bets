package es.dws.quidditch.repository;

import es.dws.quidditch.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match,Long> {
}

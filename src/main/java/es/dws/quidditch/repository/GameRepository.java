package es.dws.quidditch.repository;

import es.dws.quidditch.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {

}

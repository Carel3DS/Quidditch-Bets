package es.dws.quidditch.controller;

import es.dws.quidditch.model.Match;
import es.dws.quidditch.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class MatchController {

    @Autowired
    private MatchRepository repository;
    public void create(Match match){
        repository.save(match);
    }
    public ResponseEntity<Match> show (@PathVariable long id){
        Optional<Match> op = repository.findById(id);
        if(op.isPresent()){
            Match match = op.get();
            return new ResponseEntity<>(match, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    public void edit(Match match, Match newMatch){}
    public void delete(Match match){}
}

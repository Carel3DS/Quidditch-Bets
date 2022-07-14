package es.dws.quidditch.restcontroller;

import es.dws.quidditch.model.Game;
import es.dws.quidditch.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RequestMapping("/api")
@RestController
public class RESTgame {
    @Autowired
    private GameService gameService;

    @PostMapping("/game")
    public ResponseEntity<Game> postGame(@RequestBody Game game){
        this.gameService.post(game);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/game")
    public ResponseEntity<Game> getGame(@RequestParam long id){
        Game game = this.gameService.get(id);
        if(game!=null) {
            return new  ResponseEntity<>(game, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/game")
    public ResponseEntity<Game> putGame(@RequestParam long id, @RequestBody Game game){
        if(this.gameService.get(id) != null){
            this.gameService.put(id,game);
            return new ResponseEntity<>(game, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/game")
    public ResponseEntity<Game> deleteGame(@RequestParam long id){
        if(this.gameService.get(id) != null){
            Game deleted = this.gameService.delete(id);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

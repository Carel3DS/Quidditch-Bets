package es.dws.quidditch.restcontroller;

import es.dws.quidditch.model.Locale;
import es.dws.quidditch.model.User;
import es.dws.quidditch.service.LocaleService;
import es.dws.quidditch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RequestMapping("/api")
@RestController
public class RESTlocale {
    @Autowired
    private LocaleService localeService;
    @Autowired
    private UserService userService;

    @PostMapping("/locale")
    public ResponseEntity<Locale> postLocale(@RequestBody Locale locale, @RequestParam String userID){
        if (userService.exists(userID)){
            User user = this.userService.get(userID);
            this.localeService.post(locale,user);
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/locale/{id}")
    public ResponseEntity<Locale> getLocale(@PathVariable long id){
        Locale locale = this.localeService.get(id);
        if(locale!=null) {
            return new  ResponseEntity<>(locale, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/locale/{id}")
    public ResponseEntity<Locale> putLocale(@PathVariable long id, @RequestBody Locale locale){
        if(this.localeService.get(id) != null){
            this.localeService.put(id,locale);
            return new ResponseEntity<>(locale, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @Transactional
    @DeleteMapping("/locale/{id}")
    public ResponseEntity<Locale> deleteLocale(@PathVariable long id){
        if(this.localeService.get(id) != null){
            Locale deleted = this.localeService.delete(id);
            return new ResponseEntity<>(deleted, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

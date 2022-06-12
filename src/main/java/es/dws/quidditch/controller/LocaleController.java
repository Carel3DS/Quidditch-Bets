package es.dws.quidditch.controller;

import es.dws.quidditch.model.Locale;
import es.dws.quidditch.repository.LocaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/locale")
public class LocaleController {
    @Autowired
    private LocaleRepository repository;

    public void create(Locale locale){
        repository.save(locale);
    }
    public ResponseEntity<Locale> show (@PathVariable long id){
        Optional<Locale> op = repository.findById(id);
        if(op.isPresent()){
            Locale locale = op.get();
            return new ResponseEntity<>(locale, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
    }
    public void edit(Locale locale, Locale newLocale){}
    public void delete(Locale locale){}
}

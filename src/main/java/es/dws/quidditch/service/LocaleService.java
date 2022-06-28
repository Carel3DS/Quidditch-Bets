package es.dws.quidditch.service;

import es.dws.quidditch.model.Locale;
import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.LocaleRepository;
import es.dws.quidditch.repository.MatchRepository;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class LocaleService {

    @Autowired
    LocaleRepository localeRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MatchService matchService;
    //API REST service (ID = LocaleID+User)
    public void post(Locale locale, User user){
        user.setLocale(locale);
        this.localeRepository.save(locale);
    }
    public Optional<Locale> get (String localeID){
        return localeRepository.findByName(localeID);
    }

    public Locale get (User user){
        return user.getOwner();
    }
    public void put(String localeID, User user, Locale newLocale){
        if(user.getOwner().getName().equals(localeID) && localeID !=null){
            newLocale.setName(localeID);
            Locale oldLocale = this.localeRepository.getByName(localeID);
            newLocale.setUsers(oldLocale.getUsers());
            newLocale.setBets(oldLocale.getBets());
            this.localeRepository.save(newLocale);
        }
    }
    public Locale delete(String localeID, User user){
        Locale locale = this.localeRepository.findByName(localeID).orElse(null);
        if (locale != null){
            locale.getBets().size();
            this.localeRepository.delete(locale);
            user.setLocale(null);
        }
        return locale;
    }
    ///////////////////////////
    // SPECIFIC LOCALE SERVICES //
    ///////////////////////////
    public void cancel(Match match, String localeID){
        Locale locale = this.localeRepository.findByName(localeID).orElse(null);
        if (locale != null && locale.hasMatch(match)){
            locale.removeMatch(match);
            locale.getMatches().size();
            this.localeRepository.save(locale);
        }
    } //local destroys the match

}

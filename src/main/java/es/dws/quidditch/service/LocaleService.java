package es.dws.quidditch.service;

import es.dws.quidditch.model.Locale;
import es.dws.quidditch.model.Match;
import es.dws.quidditch.model.User;
import org.springframework.stereotype.Service;

@Service
public class LocaleService {
    //API REST service (ID = LocaleID+User)
    public void post(Locale locale, User user){}
    public void get (long localeID, User user){}
    public void put(long localeID, User user/*, smth else here*/){}
    public void delete(long localeID, User user){}
    ///////////////////////////
    // SPECIFIC LOCALE SERVICES //
    ///////////////////////////
    public void cancel(Match match){} //local destroys the match

}

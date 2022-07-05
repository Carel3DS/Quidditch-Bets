package es.dws.quidditch.security;

import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DatabaseUsersLoader {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatabaseUsersLoader() {}

    @PostConstruct
    private void initDatabase() {
        userRepository.save(new User("user@user.com","user","12345678T",
                            passwordEncoder.encode("pass"),
                            "USER"));
        
        userRepository.save(new User("admin@admin.com","admin","87654321T",
                                    passwordEncoder.encode("adminpass"),
                                    "USER","ADMIN"));
    }
}

package es.dws.quidditch.security;

import es.dws.quidditch.model.User;
import es.dws.quidditch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseUsersLoader {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public DatabaseUsersLoader() {
    }

    @PostConstruct
    private void initDatabase() {
        userRepository.save(new User("user",
                            passwordEncoder.encode("pass"),
                            new ArrayList<>(Arrays.asList("USER"))));
        
        userRepository.save(new User("admin",
                                    passwordEncoder.encode("adminpass"),
                                    new ArrayList<>(Arrays.asList("USER","ADMIN"))));
    }
}

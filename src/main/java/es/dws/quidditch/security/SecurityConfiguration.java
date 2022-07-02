package es.dws.quidditch.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    RepositoryUserDetailsService UserDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }
    //Method that allow us to configure access to webpages.
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeHttpRequests()    //To all the requests
            .antMatchers("/","index","/css/*","/js/*")
            .permitAll()
            .anyRequest()               //Every request
            .authenticated()            //Must be authenticated
            .and()
            .httpBasic();
        //Through a basic authentication mechanism

        //Public pages
        http.authorizeRequests().antMatchers("/").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/loginerror").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();

        http.authorizeRequests().antMatchers("/register").permitAll();
        http.authorizeRequests().antMatchers("/locale").permitAll();
        http.authorizeRequests().antMatchers("/games").permitAll();

        //Public endpoints
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/locale/**").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/game").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user").permitAll();

        //Authenticated endpoints
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/bet").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/bet").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/user/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/user/**").authenticated();
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/bet").authenticated();

        //Private pages and endpoints (the rest of them)
        http.authorizeRequests().anyRequest().hasRole("ADMIN");

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().defaultSuccessUrl("/private");
        http.formLogin().failureUrl("/loginerror");
        // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

        // Disable CSRF at the moment
        http.csrf().disable();

    }
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Validating user...
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword = encoder.encode("pass");
        auth.inMemoryAuthentication().withUser("user").password(encodedPassword).roles("USER");

        //Admin
        auth.inMemoryAuthentication().withUser("admin")
                .password(encoder.encode("adminpass")).roles("USER", "ADMIN");
    }
}
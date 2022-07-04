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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }
    //Method that allow us to configure access to webpages.
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //Validating user...
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //Public pages
        http
                .authorizeRequests()
                .antMatchers("/",
                             "/login",
                             "/register",
                             "/locale",
                             "/games",
                             "/js/*",
                             "/css/*")
                .permitAll();
//        http.authorizeRequests().antMatchers("/").permitAll();
        //        http.authorizeRequests().antMatchers("login").permitAll();
//        http.authorizeRequests().antMatchers("/register").permitAll();
//        http.authorizeRequests().antMatchers("/locale").permitAll();
//        http.authorizeRequests().antMatchers("/bets").permitAll();



         // http.authorizeRequests().antMatchers("/register").permitAll();

        //http.authorizeRequests().antMatchers("/games").permitAll();

        //Public endpoints
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/locale/**","/api/game").permitAll();
        //http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/game").permitAll();
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/user").permitAll();

        //Authenticated pages
        http.authorizeRequests().antMatchers("/locale").hasAnyRole("USER","ADMIN");
        //Authenticated endpoints
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/user/**").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/api/bet").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/bet").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/api/user/**").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/user/**").hasAnyRole("USER","ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/api/bet").hasAnyRole("USER","ADMIN");

        //Private pages and endpoints (the rest of them)
        http.authorizeRequests().anyRequest().hasRole("ADMIN");

        // Login form
        http.formLogin().loginPage("/login");
        http.formLogin().usernameParameter("username");
        http.formLogin().passwordParameter("password");
        http.formLogin().failureUrl("/loginerror");
        http.formLogin().defaultSuccessUrl("/logged");
         // Logout
        http.logout().logoutUrl("/logout");
        http.logout().logoutSuccessUrl("/");

        // Disable CSRF at the moment
        http.csrf().disable();

    }
}
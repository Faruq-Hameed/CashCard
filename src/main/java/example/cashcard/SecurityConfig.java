package example.cashcard;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration // tells Spring to use this class to configure Spring and Spring Boot itself.
// Any Beans specified in this class will now be available to Spring's Auto
// Configuration engine.
class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(
                request -> request // the request matcher configuration
                        .requestMatchers("/cashcards/**") // the base path for our CashCardController
                        .authenticated() // any request to /cashcards/** must be authenticated using HTTP Basic
                                         // authentication
        )
                .httpBasic(Customizer.withDefaults()) // use HTTP Basic authentication
                .csrf(csrf -> csrf.disable()); // disable CSRF for simplicity
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserBuilder userBuilder = User.builder(); // a builder for UserDetails objects
        UserDetails sarah = userBuilder // from the builder, build a UserDetails object
                .username("sarah1") // the username is sarah1
                .password(passwordEncoder.encode("abc123")) // password is sarah1Pass encoded using BCrypt
                .roles("CARD-OWNER") // assign the user the role of USER
                .build(); // build the UserDetails object

        UserDetails hankOwnsNoCard = userBuilder
                .username("hank-owns-no-cards")
                .password("qrs456")
                .roles("NON-OWNER")
                .build();

        return new InMemoryUserDetailsManager(sarah, hankOwnsNoCard); // return an in-memory user details manager with
                                                                      // the user we just
        // built
        /*
         * Spring's IoC container will find the UserDetailsService Bean and Spring Data
         * will use it when needed.
         * Spring's IoC container will find the UserDetailsService Bean and Spring Data
         * will use it when needed.
         */
    }
}
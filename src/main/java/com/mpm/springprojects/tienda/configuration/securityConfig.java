package com.mpm.springprojects.tienda.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.mpm.springprojects.tienda.services.UsuarioService;

@Configuration
@EnableWebSecurity
public class securityConfig{

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        //return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    UsuarioService myUserService(){
        return new UsuarioService();
    }
    
    /*
    @Bean
    public UserDetailsService user(){

        UserDetails user = User.builder()
        .username("user")
        .password("{noop}user*1234")
        .authorities("USER")
        .build();

        UserDetails admin = User.builder()
        .username("admin")
        .password("{noop}admin*1234")
        .authorities("USER","ADMIN")
        .build();

        return new InMemoryUserDetailsManager(user,admin);
    }
 */

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider autProvider = new DaoAuthenticationProvider(); 
        autProvider.setUserDetailsService(myUserService());
        //autProvider.setPasswordEncoder(null);
        autProvider.setPasswordEncoder(passwordEncoder());
        return autProvider;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{

        http.authorizeHttpRequests()
            .anyRequest()     // cualquier peticion
            .authenticated()  // debe autenticarse 
        .and()
            .formLogin()      // se usará un formulario
        .and()
            .httpBasic();  

        return http.build();
    }

}

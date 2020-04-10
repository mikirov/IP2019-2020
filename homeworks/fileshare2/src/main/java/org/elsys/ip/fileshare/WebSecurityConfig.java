package org.elsys.ip.fileshare;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/home", "/register")
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/homepage.html",true)
                .failureUrl("/login.html?error=true")
                .permitAll()
                .and()
                .logout()
                .permitAll();


}

    @Bean
    public PasswordEncoder passwordEncoder() {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        return passwordEncoder;
        PlainPasswordEncoder plainPasswordEncoder = new PlainPasswordEncoder();
        return plainPasswordEncoder;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        JdbcDaoImpl jdbcDaoImpl = new JdbcDaoImpl();
        jdbcDaoImpl.setDataSource(dataSource);
        return jdbcDaoImpl;
    }

}
package com.foodsite.foodsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //super.configure(auth);

       // auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
         //       .withUser("superuser")
           //     .password("12345678")
             //   .roles("ADMIN")
              //  .and()
              // .withUser("user")
              //  .password("4444")
              //  .roles("USER");

        auth.jdbcAuthentication()
               .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select username, password, active from usr where username=?")
                .authoritiesByUsernameQuery("select u.username, ur.roles from usr u inner join user_role ur on u.id = ur.user_id where u.username=?");
                //.usersByUsernameQuery("select username, password, role from user where username=?");
                //.authoritiesByUsernameQuery("select username, role from user where username=?");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/","/reg").permitAll()
                .antMatchers("/add").permitAll()
                .antMatchers("/account").permitAll()
                .antMatchers("/recipes").permitAll()
                .antMatchers("/chiefs").permitAll()
                .antMatchers("/coc").permitAll()
                .antMatchers("/wine").permitAll()
                .antMatchers("/recip/{id}").permitAll()
                .antMatchers("/user/{id}").permitAll()
                .antMatchers("/filter/{id}").permitAll()
                .antMatchers("/search").permitAll()
                .and().formLogin();

    }


}

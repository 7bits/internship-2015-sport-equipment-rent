package it.sevenbits.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/index", "/profile", "/see_announcement", "/see_announcement/**", "/resources/public/**", "/registration", "/add", "/add_announcement")
                .permitAll()
                .anyRequest().authenticated()
            .and()
               .formLogin()
                    .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/confirm", true)
            .and()
                .logout()
                    .logoutUrl("/logout")
                .permitAll();

        http
            .csrf().disable();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select email, pass, '1' from users where email=?")
                        .passwordEncoder(new BCryptPasswordEncoder())
        .authoritiesByUsernameQuery(
                "select email,  users_role from users where email=?");
    }

    @Override
    public void configure(WebSecurity webSecurity) throws Exception {
        webSecurity.ignoring().antMatchers("/resources/**");
    }

}

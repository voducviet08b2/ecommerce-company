package com.example.ecommerce.config;




import com.example.ecommerce.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    return bCryptPasswordEncoder;
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();
    http.authorizeRequests().antMatchers("/login", "/logout").permitAll();
    http.authorizeRequests().antMatchers("/product").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
    http.authorizeRequests().antMatchers("/product/admin").access("hasRole('ROLE_ADMIN')");
    http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");
    http.authorizeRequests().and().formLogin()
            .loginProcessingUrl("/login")
            .loginPage("/login")
            .defaultSuccessUrl("/product/admin")
            .failureUrl("/login?error=true")
            .usernameParameter("username")
            .passwordParameter("password")
            .and().logout().logoutUrl("/logout").logoutSuccessUrl("/logoutSuccessful");

  }

}

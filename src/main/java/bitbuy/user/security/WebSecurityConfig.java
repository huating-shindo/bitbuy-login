package bitbuy.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomDetailsService customDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customDetailsService).passwordEncoder(encoder());
//        auth.inMemoryAuthentication()
//                .withUser("user").password(encoder().encode("user")).roles("USER")
//                .and()
//                .withUser("admin").password(encoder().encode("admin")).roles("USER", "ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http.
//                authorizeRequests().antMatchers(HttpMethod.GET, "/api/login").permitAll()
//                .and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/register").permitAll()
//                .and().sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.NEVER);
        http.httpBasic()
                .and()
                .authorizeRequests().antMatchers("/**").permitAll()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().csrf().disable();
//                .sessionCreationPolicy(SessionCreationPolicy.NEVER);;
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/users/**").hasAnyRole("ADMIN")
//                .anyRequest().authenticated()
//                .and().formLogin()
//                .permitAll();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

//    @Autowired
//    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
//        auth.jdbcAuthentication().dataSource(dataSource);
//    }

}

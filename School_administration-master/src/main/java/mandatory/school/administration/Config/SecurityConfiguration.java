package mandatory.school.administration.Config;

import mandatory.school.administration.Services.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    // Restrict user access
    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http.csrf().disable();

        //Free pages
        http.authorizeRequests().antMatchers("/login", "/logout", "/course/ajaxdetails*","/fail").permitAll();

        //Can be accessed by all
        http.authorizeRequests().antMatchers("/home", "/", "/course", "/course/details*").access("isAuthenticated()");

        //For teacher only
        http.authorizeRequests().antMatchers("/course/create", "/course/edit").access("hasAnyAuthority('admin','teacher')");

        //For admins only
        http.authorizeRequests().antMatchers("/teacher/*", "/application*", "/course/*", "/admin").hasAuthority("admin");

        //For student only
        http.authorizeRequests().antMatchers("/student").hasAuthority("student");
        //Teacher Only
        http.authorizeRequests().antMatchers("/teacher").hasAuthority("teacher");

        //Acces denied page
        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/fail");

        //Login Config
        http.authorizeRequests().and().formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile")
                .failureUrl("/fail")
                .usernameParameter("username")
                .passwordParameter("password")
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/login");
    }

    // Creates an instance of authority based on user
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception
    {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
package com.qaqnus.news.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.qaqnus.news.service.UserService;
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserService userService;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception{
		httpSecurity
				.csrf()
					.disable()
				.authorizeRequests()
					.antMatchers("/registration").not().fullyAuthenticated()
					
					.antMatchers("/admin/**").hasRole("ADMIN")
					
					.antMatchers("/news").hasRole("USER")
					
					.antMatchers("/", "/resources/**").permitAll()
				
				.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/login")
					.defaultSuccessUrl("/newsList")
					.permitAll()
				.and()
					.logout()
					.permitAll()
					.logoutSuccessUrl("/");
	}
	
	protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
	}

}

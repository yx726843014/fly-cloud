package com.fly;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@SpringBootApplication
@EnableAdminServer
@EnableWebSecurity
public class FlyNewMonitorApplication extends WebSecurityConfigurerAdapter{

	public static void main(String[] args) {
		SpringApplication.run(FlyNewMonitorApplication.class, args);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().formLogin().loginPage("/login.html").permitAll().defaultSuccessUrl("/")
				.and()//Logout Form configuration
				.logout()
				.deleteCookies("remove")
				.logoutSuccessUrl("/login.html").permitAll()
				.and().authorizeRequests().antMatchers(HttpMethod.POST, "/api/applications/").permitAll()
				.antMatchers("/health","/instances").permitAll()
				.anyRequest().authenticated();
		//http.authorizeRequests().anyRequest().permitAll();
	}
}

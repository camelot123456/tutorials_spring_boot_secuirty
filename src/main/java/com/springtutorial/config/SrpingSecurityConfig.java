package com.springtutorial.config;

import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.springtutorial.config.ApplicationUserRole.*;
import static com.springtutorial.config.ApplicationUserPermission.*;

@Configuration
@EnableWebSecurity
public class SrpingSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;

	public SrpingSecurityConfig(PasswordEncoder passwordEncoder) {
		// TODO Auto-generated constructor stub
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
		.antMatchers("/api/**").hasRole(STUDENT.name())
		.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.name())
		.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.name())
		.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.name())
		.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		UserDetails acc1 = User.builder()
				.username("acc1")
				.password(passwordEncoder.encode("1"))
				.roles(ADMIN.name())
				.build();
 
		UserDetails acc2 = User.builder()
				.username("acc2")
				.password(passwordEncoder.encode("1"))
				.roles(STUDENT.name())
				.build();

		UserDetails acc3 = User.builder()
				.username("acc3")
				.password(passwordEncoder.encode("1"))
				.roles(ADMINTRAINEE.name())
				.build();
		
		return new InMemoryUserDetailsManager(acc1, acc2, acc3);
	}

}

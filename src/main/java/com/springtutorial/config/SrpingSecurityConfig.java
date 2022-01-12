package com.springtutorial.config;

import static com.springtutorial.config.ApplicationUserRole.ADMIN;
import static com.springtutorial.config.ApplicationUserRole.ADMINTRAINEE;
import static com.springtutorial.config.ApplicationUserRole.STUDENT;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
		.csrf().disable()//csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.authorizeRequests()
		.antMatchers("/", "index", "/css/*", "/js/*", "/a").permitAll()
		.antMatchers("/api/**").hasRole(STUDENT.name())
//		.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
		.anyRequest()
		.authenticated()
		.and()
		.formLogin()
		.loginPage("/login").permitAll()
		.defaultSuccessUrl("/courses", true)
		.and()
		.rememberMe().tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
		.key("secret")
		.and()
		.logout().logoutUrl("/logout")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
		.clearAuthentication(true)
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID", "remember-me")
		.logoutSuccessUrl("/login");
	}

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		// TODO Auto-generated method stub
		UserDetails acc1 = User.builder()
				.username("acc1")
				.password(passwordEncoder.encode("1"))
//				.roles(ADMIN.name())
				.authorities(ADMIN.getGrantedAuthorities())
				.build();
 
		UserDetails acc2 = User.builder()
				.username("acc2")
				.password(passwordEncoder.encode("1"))
//				.roles(STUDENT.name())
				.authorities(STUDENT.getGrantedAuthorities())
				.build();

		UserDetails acc3 = User.builder()
				.username("acc3")
				.password(passwordEncoder.encode("1"))
//				.roles(ADMINTRAINEE.name())
				.authorities(ADMINTRAINEE.getGrantedAuthorities())
				.build();
		
		return new InMemoryUserDetailsManager(acc1, acc2, acc3);
	}

}

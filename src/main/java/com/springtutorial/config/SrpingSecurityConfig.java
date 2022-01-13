package com.springtutorial.config;

import static com.springtutorial.config.ApplicationUserRole.STUDENT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.springtutorial.auth.ApplicationUserService;
import com.springtutorial.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SrpingSecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final ApplicationUserService applicationUserService;

	public SrpingSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
		// TODO Auto-generated constructor stub
		this.passwordEncoder = passwordEncoder;
		this.applicationUserService = applicationUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http
		.csrf().disable()//csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager()))
		.authorizeRequests()
			.antMatchers("/", "index", "/css/*", "/js/*").permitAll()
			.antMatchers("/api/**").hasRole(STUDENT.name())
	//		.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
	//		.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
	//		.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(COURSE_WRITE.getPermission())
	//		.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
			.anyRequest()
			.authenticated();
//		.and()
//		.formLogin()
//			.loginPage("/login").permitAll()
//			.usernameParameter("username")
//			.passwordParameter("password")
//			.defaultSuccessUrl("/courses", true)
//		.and()
//		.rememberMe()
//			.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
//			.key("secret")
//			.rememberMeParameter("remember-me")
//		.and()
//		.logout()
//			.logoutUrl("/logout")
//			.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
//			.clearAuthentication(true)
//			.invalidateHttpSession(true)
//			.deleteCookies("JSESSIONID", "remember-me")
//			.logoutSuccessUrl("/login");
	}
	
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(applicationUserService);
		return provider;
	}

//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		// TODO Auto-generated method stub
//		UserDetails acc1 = User.builder()
//				.username("acc1")
//				.password(passwordEncoder.encode("1"))
////				.roles(ADMIN.name())
//				.authorities(ADMIN.getGrantedAuthorities())
//				.build();
// 
//		UserDetails acc2 = User.builder()
//				.username("acc2")
//				.password(passwordEncoder.encode("1"))
////				.roles(STUDENT.name())
//				.authorities(STUDENT.getGrantedAuthorities())
//				.build();
//
//		UserDetails acc3 = User.builder()
//				.username("acc3")
//				.password(passwordEncoder.encode("1"))
////				.roles(ADMINTRAINEE.name())
//				.authorities(ADMINTRAINEE.getGrantedAuthorities())
//				.build();
//		
//		return new InMemoryUserDetailsManager(acc1, acc2, acc3);
//	}

}

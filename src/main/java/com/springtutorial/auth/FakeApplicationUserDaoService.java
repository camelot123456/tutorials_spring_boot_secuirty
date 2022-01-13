package com.springtutorial.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import static com.springtutorial.config.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		// TODO Auto-generated constructor stub
		this.passwordEncoder = passwordEncoder;
	}
	
	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		// TODO Auto-generated method stub
		return getApplicationUsers().stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}

	private List<ApplicationUser> getApplicationUsers() {
		List<ApplicationUser> applicationUsers = Lists.newArrayList(
				new ApplicationUser(
						ADMIN.getGrantedAuthorities(), 
						passwordEncoder.encode("1"), 
						"acc4", 
						true, 
						true, 
						true, 
						true),
				
				new ApplicationUser(
						STUDENT.getGrantedAuthorities(), 
						passwordEncoder.encode("1"), 
						"acc5", 
						true, 
						true, 
						true, 
						true),
				
				new ApplicationUser(
						ADMINTRAINEE.getGrantedAuthorities(), 
						passwordEncoder.encode("1"), 
						"acc6", 
						true, 
						true, 
						true, 
						true)
				);
		return applicationUsers;
	}
	
}

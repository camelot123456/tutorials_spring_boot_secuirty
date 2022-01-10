package com.springtutorial.config;

import java.util.Set;
import com.google.common.collect.Sets;

import static com.springtutorial.config.ApplicationUserPermission.*;

public enum ApplicationUserRole {

	ADMIN(Sets.newHashSet()),
	STUDENT(Sets.newHashSet(STUDENT_READ, STUDENT_WRITE, COURSE_READ, COURSE_WRITE)),
	ADMINTRAINEE(Sets.newHashSet(STUDENT_READ, COURSE_READ));
	
	private final Set<ApplicationUserPermission> permissions;
	
	private ApplicationUserRole(Set<ApplicationUserPermission> permissions) {
		// TODO Auto-generated constructor stub
		this.permissions = permissions;
	}
	
	public Set<ApplicationUserPermission> getPermissions() {
		return permissions;
	}
	
}

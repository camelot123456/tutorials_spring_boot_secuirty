package com.springtutorial.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springtutorial.config.ApplicationUserPermission;
import com.springtutorial.config.ApplicationUserRole;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/management/api/v1/students")
@Slf4j
public class StudentManagementController {

	private static final List<Student> STUDENTS = Arrays.asList(
			new Student(1, "Bao Nguyen"), 
			new Student(2, "Dong lan"),
			new Student(3, "Hoang Quang"));
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
	public List<Student> getAllStudents() {
		log.info("{}, {}, {}", ApplicationUserRole.ADMIN, ApplicationUserRole.ADMINTRAINEE, ApplicationUserRole.STUDENT);
		log.info("{}, {}, {}, {}", ApplicationUserPermission.COURSE_READ, ApplicationUserPermission.COURSE_WRITE, ApplicationUserPermission.STUDENT_READ, ApplicationUserPermission.STUDENT_WRITE);
		log.info("{}, {}, {}", ApplicationUserRole.ADMIN.name(), ApplicationUserRole.ADMINTRAINEE.name(), ApplicationUserRole.STUDENT.name());
		log.info("{}, {}, {}, {}", ApplicationUserPermission.COURSE_READ.name(), ApplicationUserPermission.COURSE_WRITE.name(), ApplicationUserPermission.STUDENT_READ.name(), ApplicationUserPermission.STUDENT_WRITE.name());
		log.info("{}, {}, {}", ApplicationUserRole.ADMIN.getPermissions(), ApplicationUserRole.ADMINTRAINEE.getPermissions(), ApplicationUserRole.STUDENT.getPermissions());
		log.info("{}, {}, {}, {}", ApplicationUserPermission.COURSE_READ.getPermission(), ApplicationUserPermission.COURSE_WRITE.getPermission(), ApplicationUserPermission.STUDENT_READ.getPermission(), ApplicationUserPermission.STUDENT_WRITE.getPermission());
		return STUDENTS;
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('course:write')")
	public void registerNewStudent(@RequestBody Student student) {
		System.out.println(student);
	}
	
	@DeleteMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('course:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId) {
		System.out.println(studentId);
	}
	
	@PutMapping(path = "{studentId}")
	@PreAuthorize("hasAuthority('course:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId, 
			@RequestBody Student student) {
		System.out.println(String.format("%s %s", studentId, student));
	}
}

package com.springtutorial;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;

public class TestSteamJava {

	public static void main(String[] args) {
		List<Person> persons = getPeople();
		
		persons.forEach(System.out::println);
//		-------------------------------	Filter -------------------------------
		System.out.println("\n Use Filter \n");
		
		persons.stream()
			.filter(person -> person.getGender().equals(EGender.FEMALE))
			.collect(Collectors.toList())
			.forEach(System.out::println);
		
//		-------------------------------	Sort -------------------------------
		
		System.out.println("\n Use Sort \n");
		
		persons.stream()
			.sorted(Comparator.comparing(Person::getAge))
			.collect(Collectors.toList())
			.forEach(System.out::println);

//		-------------------------------	Sort Reverse -------------------------------		

		System.out.println("\n Use Sort Reverse\n");
		
		persons.stream()
			.sorted(Comparator.comparing(Person::getAge).reversed())
			.collect(Collectors.toList())
			.forEach(System.out::println);
		
//		-------------------------------	Sort by age then sort by gender -------------------------------
		
		System.out.println("\n Use Sort by age then sort by gender\n");
		
		persons.stream()
			.sorted(Comparator.comparing(Person::getAge).thenComparing(Person::getGender))
			.collect(Collectors.toList())
			.forEach(System.out::println);

//		-------------------------------	All match -------------------------------
		
		System.out.println("\n Use All match\n");
		
		int age = 18;
		System.out.println("Tat ca deu tren " +age+ " tuoi? " + (persons.stream()
			.allMatch(person -> person.getAge() > age) ? "Yes" : "No"));
	
//		-------------------------------	Any match -------------------------------	
	
		System.out.println("\n Use Any match\n");
		
		System.out.println("Co tuoi la " +age+ "? " + (persons.stream()
			.anyMatch(person -> person.getAge() == age) ? "Yes" : "No"));
		
//		------------------------------- None match -------------------------------
		
		System.out.println("\n Use None match\n");
		
		System.out.println("Khong ton tai tuoi la " +age+ "? " + (persons.stream()
			.noneMatch(person -> person.getName().equals("Name")) ? "Yes" : "No"));

//		-------------------------------	Max -------------------------------
		
		System.out.println("\n Use Max\n");
		
		persons.stream()
			.max(Comparator.comparing(Person::getAge))
			.ifPresent(System.out::println);

//		-------------------------------	Min -------------------------------
		
		System.out.println("\n Use Min\n");
		
		persons.stream()
			.min(Comparator.comparing(Person::getAge))
			.ifPresent(System.out::println);
		
//		-------------------------------	Group -------------------------------
		
		System.out.println("\n Use Group\n");
		
		Map<EGender, List<Person>> groupByGender = persons.stream()
			.collect(Collectors.groupingBy(Person::getGender));
		
		groupByGender.forEach((gender, people1) -> {
			System.out.println(gender.getGender());
			people1.forEach(System.out::println);
		});
		
		Optional<String> oldestFemaleAge = persons.stream()
				.filter(person -> person.getGender().equals(EGender.FEMALE))
				.max(Comparator.comparing(Person::getAge))
				.map(Person::getName);
		
		oldestFemaleAge.ifPresent(System.out::println);
	}
	
	
	public static List<Person> getPeople() {
		
		return List.of(
				new Person("Name1", 21, EGender.MALE),
				new Person("Name2", 19, EGender.FEMALE),
				new Person("Name3", 17, EGender.MALE),
				new Person("Name4", 23, EGender.FEMALE),
				new Person("Name5", 18, EGender.MALE),
				new Person("Name6", 16, EGender.MALE),
				new Person("Name7", 24, EGender.MALE),
				new Person("Name8", 26, EGender.MALE),
				new Person("Name9", 14, EGender.MALE),
				new Person("Name0", 25, EGender.FEMALE));
		
	}
	
}

@Data
@AllArgsConstructor
class Person {
	
	private String name;
	private Integer age;
	private EGender gender;
	
}

enum EGender {
	MALE("gender:male"),
	FEMALE("gender:female");
	
	public final String gender;

	private EGender(String gender) {
		this.gender = gender;
	}
	
	public String getGender() {
		return gender;
	}
}
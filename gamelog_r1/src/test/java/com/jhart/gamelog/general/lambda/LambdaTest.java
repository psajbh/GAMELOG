package com.jhart.gamelog.general.lambda;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;

import lombok.Data;

//refs:
//https://medium.freecodecamp.org/learn-these-4-things-and-working-with-lambda-expressions-b0ab36e0fffc
public class LambdaTest {

	//functional interface
	interface NumericTest {
		boolean computeTest(int n); 
	}
	
	//functional interface
	interface MyGreeting {
		String processName(String str);
	}
	
	private NumericTest isEven = (n) -> (n % 2) == 0;
	private NumericTest isNegative = (n) -> (n < 0);
	private MyGreeting morningGreeting = (str) -> "Good Morning " + str + "!";
	@SuppressWarnings("unused")
	private MyGreeting eveningGreeting = (str) -> "Good Evening " + str + "!";
	
	@SuppressWarnings("deprecation")
	@Test
	public void testFunctionalInterfacess() {
		Assert.assertFalse(isEven.computeTest(5));
		Assert.assertTrue(isNegative.computeTest(-5));
		
		String morning = morningGreeting.processName("Joe");
		System.out.println("morning: " + morning);
		
	}
	
	@Test
	public void testBlockLambdaExpression() throws Exception {
		
	}
	
	//return findAllActive(true, BudgesUser.LAST_NAME).stream().filter(x -> x.getRole() != null && !LdapDAO.GROUP_NONE.equals(x.getRole())).collect(Collectors.toList());


	
	@SuppressWarnings("unused")
	@Test
	public void testPet() throws Exception{
		List<Pet> pets = createPets();
		List<Pet> dogs = createPets().stream().filter(x -> x.getType().equals("Dog")).collect(Collectors.toList());
		List<Pet> cats = createPets().stream().filter(x -> x.getType().equals("Cat")).collect(Collectors.toList());
		List<String> names = createPets().stream().map(Pet :: getName).collect(Collectors.toList());
		System.out.println();
	}
	
	private List<Pet> createPets(){
		List<Pet> pets = new ArrayList<>();
		
		Pet pet1 = new Pet();
		pet1.setName("Bella");
		pet1.setType("Dog");
		pets.add(pet1);
		
		Pet pet2 = new Pet();
		pet2.setName("Lexy");
		pet2.setType("Dog");
		pets.add(pet2);
		
		Pet pet3 = new Pet();
		pet3.setName("Jason");
		pet3.setType("Cat");
		pets.add(pet3);
		
		return pets;
		
	}
	
}

@Data
class Pet {
	String type;
	String name;
}




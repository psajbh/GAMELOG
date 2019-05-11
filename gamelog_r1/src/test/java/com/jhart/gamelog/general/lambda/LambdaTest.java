package com.jhart.gamelog.general.lambda;

import org.junit.Test;

import junit.framework.Assert;

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
	
	
}

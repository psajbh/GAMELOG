package com.jhart.gamelog.transform;

import org.junit.Assert;
import org.junit.Test;

public class ParkTransformerTest {

	// this method functionality move to Utils class.
	@Test
	public void testTransformDateStringToLocalDateTest() {
		String test = "09/11/1880";  //park dates string mo/day/fullYear
		String[] testArray = test.split("/");
		StringBuilder sb = new StringBuilder();
		sb.append(testArray[2] +"-");
		sb.append(testArray[0] +"-");
		sb.append(testArray[1]);
		Assert.assertEquals("1880-09-11", sb.toString());
	}
	
	@Test
	public void testTransformPersonDateStringToLocalDateTest() {
		String test = "5/7/2001";
		String desiredDate = "05/07/2001";
		String[] testArray = test.split("/");
		StringBuilder sb = new StringBuilder();
		int count = 0;
		for (String s : testArray){
			count++;
			if (s.length() < 2) {
				sb.append("0" + s);
				if (count < 3) {
					sb.append("/");
				}
			}
			else {
				sb.append(s);
			}
		}
		String refactoredDate = sb.toString();
		Assert.assertEquals(desiredDate, refactoredDate);
		System.out.println();
	}


}

package com.jhart.gamelog.api;

import java.util.List;
import java.util.Map;

public interface Parser<T> {

	public final static String EMPTY = "-1";
	public static int CLEAN_REPEAT = 4;
	public static String STRING_INT_EXCEPTION = "Exception converting a null to an int";
	public static String NUMBER_FORMAT_EXCEPTION = "failure attempting to convert %s to int";

	public static String UTF_8 = "UTF-8";
	public static String EMPTY_COMMA = ",,";
	public static String NULL_ENTRY = ",-1,";
	public static String EMPTY_QUOTE = "\"";
	public static String NULL = "";
	
	
    /** process via parser a List of object from a String representing a file.
     *  The file has a year embedded in it which will be used as the Map key.  
     *  The List of objects will be the map value.
     * @param source - String
     * @return Map<String, List<T>
     */
	Map<String, List<T>> process(String source);

}

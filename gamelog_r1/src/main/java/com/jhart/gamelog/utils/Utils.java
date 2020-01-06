package com.jhart.gamelog.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jhart.gamelog.api.Parser;
import com.jhart.gamelog.exception.GamelogException;
import com.jhart.gamelog.parser.ImportDataManager;
import com.jhart.gamelog.transform.PersonTransformer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {
	private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
	private static final  String HYPHEN = "-";
	
	public static final String MODAYEAR_PATTERN =  "MM/dd/yyyy";
	public static final String YRMODA_SANFORMAT =  "yyyyMMdd";
	private static final DateTimeFormatter monthDaydYearFormatter = DateTimeFormatter.ofPattern(MODAYEAR_PATTERN);
	
	private Utils() {}

	//not sure about this.  May delete.
	public static String convertEntityDateToModelDate(LocalDate date) {
		return null;
	}
	
	private static LocalDate getLocalDate(String date, String pattern) throws DateTimeParseException{
		LocalDate localDate = null;
		if (pattern.equals(Utils.MODAYEAR_PATTERN)) {
			localDate = LocalDate.parse(date, monthDaydYearFormatter);
		}
		
		return localDate;
	}
	
	public static LocalDate getFormattedLocalDate(String date, String pattern) {
		LOG.debug("Utils:getFormattedLocalDate - date: " + date + " pattern: " + pattern);
		if (null == date) {
			return null;
		}
		
		if (date.length() > 10) {
			return null;
		}
		
		if (pattern.equals(Utils.YRMODA_SANFORMAT) && (date.length() == 8)){
		    LOG.debug("parsing gamelog provided date in format yyyyMMDD");
		    StringBuilder sb = new StringBuilder();
		    String yr = date.substring(0,4);
		    String mo = date.substring(4,6);
		    String da = date.substring(6);  //20170402 - > 201-0-02
		    sb.append(yr).append(Utils.HYPHEN).append(mo).append(Utils.HYPHEN).append(da);
		    LocalDate localDate = LocalDate.parse(sb.toString());
		    return localDate;
		}
		
		LOG.debug("Utils : getFormattedLocalDate - processing for a localDate with: " + date);
		if (date.length() < 10) {
			String[] testArray = date.split("/");
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
			date = sb.toString();
		}
		
		LocalDate ld = getLocalDate(date, pattern);
		LOG.debug("Utils : getFormattedLocalDate - localDate: " + ld.toString());
		return ld;
	}
	
	public static String convertMDY_LocalDateString(String mdyDateStr) {
		String[] mdyArray = mdyDateStr.split("/");
		StringBuilder sb = new StringBuilder();
		sb.append(mdyArray[2]);
		sb.append(Utils.HYPHEN);
		sb.append(mdyArray[0]);
		sb.append(Utils.HYPHEN);
		sb.append(mdyArray[1]);
		return sb.toString();
	}
	
	public static String cleanLine(String line, int iterations) {
        for (int i = 0; i < iterations; i++) {
            line = line.replaceAll(Parser.EMPTY_COMMA, Parser.NULL_ENTRY);
        }
        return line;
	
	}

	public static String stripQuotes(String element) {
		return element.replace(Parser.EMPTY_QUOTE, Parser.NULL);
	}

	public static int convertStringToInt(String s) throws GamelogException{
		//log.debug("convertStringToInt- " + s);
		int rValue;
		if (StringUtils.isBlank(s)) {
			GamelogException gle = new GamelogException();
			gle.setMessage(Parser.STRING_INT_EXCEPTION);
			LOG.error(gle.getMessage(),gle);
			return -1;
		}
		
		try {
			rValue = Integer.parseInt(s);
		}
		catch(NumberFormatException nfe) {
			GamelogException gle = new GamelogException(nfe);
			gle.setMessage(String.format(Parser.NUMBER_FORMAT_EXCEPTION, s));
			LOG.error(gle.getMessage(),gle);
			rValue = -2;
		}
		
		return rValue;
	}
	
	
    public synchronized static String getGamelogCode(String season, long id) {
        String padding = getPaddingZeros(id);
        return season + "-" + padding;
    }
    
    private static String getPaddingZeros(long idCode) {
        
        String codeId = String.valueOf(idCode);
        String idPad = null;
        
        switch(codeId.length()) {
        
            case 1:
                idPad = "000";
                break;
            
            case 2:
                idPad = "00";
                break;
                
            case 3:
                idPad = "0";
                break;
                
            default :
                idPad = "";
        }
        
        return idPad + codeId;
    }

	
	
	
}

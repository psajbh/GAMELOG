package com.jhart.gamelog.utils.dbconfig;

import org.jasypt.util.text.BasicTextEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import com.jhart.gamelog.GameLogParseInitializer;

public class Decryptor {
    private static final Logger LOG = LoggerFactory.getLogger(Decryptor.class);
	private static final String RANDOMDATA = "fox-2_TurnTheDroneBoolaBoola_oh_crap";
	private BasicTextEncryptor textEncryptor;

	public Decryptor() {
		init();
	}

	private void init() {
		textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPasswordCharArray(Decryptor.RANDOMDATA.toCharArray());
	}
	
	public String decrypt(String encryptValue) {
	    if (LOG.isDebugEnabled()) {
	        String decryptValue = textEncryptor.decrypt(encryptValue);
	        LOG.debug(String.format("decrypting: %s to: %s", encryptValue, decryptValue));
	        return decryptValue;
	        
	    }
	    else {
	        return textEncryptor.decrypt(encryptValue);
	    }
	}

	//TODO: move to tests class.
	public static void main(String[] args) {
/*		String test1 = "Y0+iYzoU4HHRvGmV8IrJhYpH2O35AQDX";
		String test2 = "osnfhGOqRgDo2/rBaT80TQ==";
		Decryptor decryptor = new Decryptor();
		String decryptedText = decryptor.decrypt(test1);
		System.out.println("decryptedText 1: " + decryptedText);

		decryptedText = decryptor.decrypt(test2);
		System.out.println("decryptedText: 2:" + decryptedText);
		
		System.out.println("done");
*/		
	}
}

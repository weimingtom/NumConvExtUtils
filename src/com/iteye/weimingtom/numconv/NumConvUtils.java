package com.iteye.weimingtom.numconv;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumConvUtils {
	// Constants: 
	public final static double MAXIMUM_NUMBER = 99999999999.99; 
    // Predefine the radix characters and currency symbols for output: 
	public final static String CN_ZERO = "零"; 
	public final static String CN_ONE = "壹"; 
	public final static String CN_TWO = "贰"; 
	public final static String CN_THREE = "叁"; 
	public final static String CN_FOUR = "肆"; 
	public final static String CN_FIVE = "伍"; 
	public final static String CN_SIX = "陆"; 
	public final static String CN_SEVEN = "柒"; 
	public final static String CN_EIGHT = "捌"; 
	public final static String CN_NINE = "玖"; 
	public final static String CN_TEN = "拾"; 
	public final static String CN_HUNDRED = "佰"; 
	public final static String CN_THOUSAND = "仟"; 
	public final static String CN_TEN_THOUSAND = "万"; 
	public final static String CN_HUNDRED_MILLION = "亿"; 
	public final static String CN_SYMBOL = "人民币"; 
	public final static String CN_DOLLAR = "元"; 
	public final static String CN_TEN_CENT = "角"; 
	public final static String CN_CENT = "分"; 
	public final static String CN_INTEGER = "整"; 
	
	public static String convertCurrency(String currencyDigits) {  
		// Variables: 
	    String integral = "";    // Represent integral part of digit number. 
	    String decimal = "";    // Represent decimal part of digit number. 
	    String outputCharacters = "";    // The output result. 
	    String[] parts = new String[]{}; 
	    String[] digits = new String[]{};
	    String[] radices = new String[]{};
	    String[] bigRadices = new String[]{};
	    String[] decimals = new String[]{}; 
	    int zeroCount; 
	    int i, p;
	    String d = ""; 
	    int quotient, modulus; 
	     
	    // Validate input string: 
	    if (currencyDigits == null || currencyDigits.equals("")) { 
	        System.err.println("Empty input!"); 
	        return ""; 
	    } 
	    Matcher matcher;
		matcher = Pattern.compile("[^,.\\d]").matcher(currencyDigits);
	    if (matcher.find() == true) { 
	    	System.err.println("Invalid characters in the input string!"); 
	        return ""; 
	    } 
	    matcher = Pattern.compile("^((\\d{1,3}(,\\d{3})*(.((\\d{3},)*\\d{1,3}))?)|(\\d+(.\\d+)?))$").matcher(currencyDigits);
	    if (matcher.find() == false) { 
	    	System.err.println("Illegal format of digit number!"); 
	        return ""; 
	    } 
	     
	    // Normalize the format of input digits: 
	    currencyDigits = currencyDigits.replaceAll(",", "");    // Remove comma delimiters. 
	    if (!currencyDigits.equals("0")) {
	    	currencyDigits = currencyDigits.replaceAll("^0+", "");    // Trim zeros at the beginning. 
	    }
	    // Assert the number is not greater than the maximum number. 
	    double currencyDigitsValue = 0;
	    try {
	    	currencyDigitsValue = Double.parseDouble(currencyDigits);
		    if (currencyDigitsValue > MAXIMUM_NUMBER) { 
		    	System.err.println("Too large a number to convert!"); 
		        return ""; 
		    }
	    } catch (NumberFormatException e) {
	    	System.err.println("Not a number to convert!"); 
	    	return "";
	    }
	     
	    // Process the coversion from currency digits to characters: 
	    // Separate integral and decimal parts before processing coversion: 
	    parts = currencyDigits.split("\\."); 
	    if (parts.length > 1) { 
	        integral = parts[0]; 
	        decimal = parts[1]; 
	        // Cut down redundant decimal digits that are after the second. 
	        if (decimal.length() >= 2) {
	        	decimal = decimal.substring(0, 2);
	        }
	    } else { 
	        integral = parts[0]; 
	        decimal = ""; 
	    } 
	    // Prepare the characters corresponding to the digits: 
	    digits = new String[]{CN_ZERO, CN_ONE, CN_TWO, CN_THREE, CN_FOUR, CN_FIVE, CN_SIX, CN_SEVEN, CN_EIGHT, CN_NINE}; 
	    radices = new String[]{"", CN_TEN, CN_HUNDRED, CN_THOUSAND}; 
	    bigRadices = new String[]{"", CN_TEN_THOUSAND, CN_HUNDRED_MILLION}; 
	    decimals = new String[]{CN_TEN_CENT, CN_CENT}; 
	    // Start processing: 
	    outputCharacters = ""; 
	    // Process integral part if it is larger than 0: 
	    long integralNum = -1;
	    try {
	    	integralNum = Long.parseLong(integral);
	    } catch (NumberFormatException e) {
	    	System.err.println("error 1");
        	return "";
	    }
	    if (integralNum > 0) { 
	        zeroCount = 0; 
	        for (i = 0; i < integral.length(); i++) { 
	            p = integral.length() - i - 1; 
	            d = integral.substring(i, i + 1); 
	            quotient = p / 4; 
	            modulus = p % 4; 
	            if (d.equals("0")) { 
	                zeroCount++; 
	            } else { 
	                if (zeroCount > 0) { 
	                    outputCharacters += digits[0]; 
	                } 
	                zeroCount = 0; 
	                int dNum = -1;
	                try {
	                	dNum = Integer.parseInt(d);
	                } catch (NumberFormatException e) {
	                	System.err.println("error 2");
	                	return "";
	                }
	                if (dNum >= 0 && dNum < digits.length && 
	                	modulus >= 0 && modulus < radices.length) {
	                	outputCharacters += digits[dNum] + radices[modulus];
	                } else {
	                	System.err.println("error 3");
	                	return "";
	                }
	            } 
	            if (modulus == 0 && zeroCount < 4) { 
	                outputCharacters += bigRadices[quotient]; 
	            }
	        } 
	        outputCharacters += CN_DOLLAR; 
	    } 
	    // Process decimal part if there is: 
	    if (decimal != "") { 
	        for (i = 0; i < decimal.length(); i++) { 
	            d = decimal.substring(i, i + 1); 
	            if (!d.equals("0")) {
	                int dNum = -1;
	                try {
	                	dNum = Integer.parseInt(d);
	                } catch (NumberFormatException e) {
	                	System.err.println("error 4");
	                	return "";
	                }
	                if (dNum >= 0 && dNum < digits.length && 
		                i >= 0 && i < decimals.length) {
	                	outputCharacters += digits[dNum] + decimals[i]; 
	                } else {
	                	System.err.println("error 5");
	                	return "";
	                }
	            } 
	        } 
	    } 
	    // Confirm and return the final output string: 
	    if (outputCharacters == null || outputCharacters.equals("")) { 
	        outputCharacters = CN_ZERO + CN_DOLLAR; 
	    } 
	    if (decimal == null || decimal.equals("")) { 
	        outputCharacters += CN_INTEGER; 
	    } 
	    outputCharacters = CN_SYMBOL + outputCharacters; 
	    return outputCharacters; 
	} 
}



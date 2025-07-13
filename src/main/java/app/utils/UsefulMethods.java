package app.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UsefulMethods {
    public static int factorial(int n) {
        int fact = 1; // this  will be the result
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
    
    public static Double getNumber(String s){
    	return !s.equals("-") ? Double.valueOf(s.replace(",", ".")) : 0;
    }
    
    public static Date getDate(String startDateString){ // esempio 13/04/1985
    	
    	Date startDate = null;
		DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ITALIAN);
		try {
			startDate = format.parse(startDateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return startDate;
    }
    	
    
    
    
    
}
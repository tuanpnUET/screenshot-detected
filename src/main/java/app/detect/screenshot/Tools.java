package app.detect.screenshot;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;


public class Tools {
	
	public static int timedown = -1000000;
	
	public static int getTimedown() {
		return timedown;
	}
	
	public static void setTimedown(int timedown) {
		Tools.timedown = timedown;
	}

	public static String getCurrentTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		String string = dateFormat.format(date);
		return string;
	}
	
	public static void countDown(final int maximum, final int current) {
		final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
		final int TIME_DELAY = 15;
        final Runnable runnable = new Runnable() {
        	int countdownStarter = current;
    		int maximumCountdown = maximum | 60;
            public void run() {
            	Tools.timedown = countdownStarter;
            	countdownStarter--;
                if (countdownStarter == 0) {
                	countdownStarter = maximumCountdown + TIME_DELAY;
                }
            }
        };
        
        scheduler.scheduleAtFixedRate(runnable, 0, 1, SECONDS);
	}
	
	public static String stringDetected(String value) {
		String result = "";
		String[] array = new String[value.length()];
		for (int a = 0; a < value.length(); a++) {
			array[a] = String.valueOf(value.charAt(a));
		}
		String[] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
		for(int i = 0; i < array.length; i++) {
			boolean isNumber = false;
			for (int j = 0; j < numbers.length; j++) {
				if (array[i].equals(numbers[j])) {
					isNumber = true;
				}	
			}
			if (isNumber) {
				result = result + array[i];
			}
		}
		return result;
	}
	
	public static <T extends Object> String checkType(T object) {    
	    if (object instanceof Integer)
	       return "Integer";
	    else if(object instanceof Double)
	    	return "Double";
	    else if(object instanceof Float)
	    	return "Float";
	    else if(object instanceof List)
	    	return "List";
	    else if(object instanceof Set)
	    	return "Set";
	    else if(object instanceof String)
	    	return "String";
		return "Not detected";
	}
	
}
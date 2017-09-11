package com.EMS.utility;

import java.util.Calendar;

public class DayChecker {

	public static Boolean firstDayChecker(){
		if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1) {
		    System.out.println ("Today is the first day of the month");
		    return true;
		}else{
			return false;
		}
	}
}

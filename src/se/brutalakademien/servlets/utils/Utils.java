package se.brutalakademien.servlets.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class Utils
{
	public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+02:00");
	
	private static final int FLUMMEN_PRICE = 349;
	private static final int FLUMMEN_PRICE_WITH_BED = 449;
	
	private static Calendar openAnmalan = null;
	private static Calendar closeAnmalan = null;
	
	static
	{
		
		openAnmalan = Calendar.getInstance();
		openAnmalan.setTimeZone(TIME_ZONE);
		openAnmalan.set(2014, 2, 20, 20, 14, 00);
		openAnmalan.setTimeZone(TIME_ZONE);
		
		closeAnmalan = Calendar.getInstance();
		closeAnmalan.setTimeZone(TIME_ZONE);
		closeAnmalan.set(2014, 03, 02, 23, 59, 59);
		closeAnmalan.setTimeZone(TIME_ZONE);
	}
	
	public static boolean isTimeForCountdown()
	{
		Calendar now = Calendar.getInstance();
		now.setTimeZone(TIME_ZONE);
		return now.before(openAnmalan);
	}
	
	public static boolean isAnmalanOpen()
	{
		// return false;
		Calendar now = Calendar.getInstance();
		now.setTimeZone(TIME_ZONE);
		return now.after(openAnmalan) && now.before(closeAnmalan);
	}
	
	public static long getOpenAnmalanTimeInMillis()
	{
		return openAnmalan.getTimeInMillis();
	}
	
	public static long getCloseAnmalanTimeInMillis()
	{
		return closeAnmalan.getTimeInMillis();
	}
	
	public static int getFlummenPrice(boolean needBed)
	{
		if (needBed)
		{
			return FLUMMEN_PRICE_WITH_BED;
		}
		else
		{
			return FLUMMEN_PRICE;
		}
	}
	
}

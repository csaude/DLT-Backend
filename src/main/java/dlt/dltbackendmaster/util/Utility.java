package dlt.dltbackendmaster.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import dlt.dltbackendmaster.domain.Beneficiaries;

public class Utility {
	public static String getSiteURL(HttpServletRequest request) {
		String siteURL = request.getRequestURL().toString();
		return siteURL.replace(request.getServletPath(), "");
	}

	public static String determineAgeBand(Beneficiaries beneficiary) {
		int age = calculateAge(beneficiary.getDateOfBirth());

		if (age >= 9 && age <= 14)
			return "9-14";
		else if (age >= 15 && age <= 19) {
			return "15-19";
		} else if (age >= 20 && age <= 24) {
			return "20-24";
		} else {
			return "25-29";
		}
	}

	public static int calculateAge(Date birthDate) {
		// validate inputs ...
		return dateDiffInYears(birthDate, new Date());
	}

	public static int dateDiffInYears(Date birthDate, Date date) {
		// validate inputs ...
		DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		int d1 = Integer.parseInt(formatter.format(birthDate));
		int d2 = Integer.parseInt(formatter.format(date));
		int age = (d2 - d1) / 10000;
		return age;

	}

	public static Date nMonthsDate(Date date, int months) {
		Date today = new Date();

		Calendar cal = Calendar.getInstance();
		cal.setTime(today);

		// Subtracting time
		cal.add(Calendar.MONTH, -months);

		// convert calendar to date
		Date modifiedDate = cal.getTime();

		return modifiedDate;
	}

	public static Date atStartOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	public static Date atEndOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return localDateTimeToDate(endOfDay);
	}

	private static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	private static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}
}

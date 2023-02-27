package dlt.dltbackendmaster.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import dlt.dltbackendmaster.domain.Beneficiaries;

public class Utility
{
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
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        int d1 = Integer.parseInt(formatter.format(birthDate));
        int d2 = Integer.parseInt(formatter.format(new Date()));
        int age = (d2 - d1) / 10000;
        return age;
    }
}

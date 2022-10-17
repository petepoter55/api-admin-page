package com.apiadminpage.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class UtilityTools {
    public String randomNumber(int length) {
        String alphabet = "1234567890";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            char randomChar = alphabet.charAt(index);

            sb.append(randomChar);
        }

        String randomString = sb.toString();
        return randomString;
    }

    public boolean checkPassphrases(String phrases, String pass)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean status = true;

        String passwordRequest = hashSha256(pass);
        if (!passwordRequest.equals(phrases)) {
            status = false;
        }
        return status;
    }

    public boolean checkOldPassword(String oldPass, String pass)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        boolean status = true;

        String passwordRequest = hashSha256(pass);
        String passwordDatabase = hashSha256(oldPass);
        if (!passwordRequest.equals(passwordDatabase)) {
            status = false;
        }
        return status;
    }

    public String hashSha256(String msg)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(msg.getBytes("UTF-8")); // Change this to "UTF-16" if needed
        byte[] digest = md.digest();
        return String.format("%064x", new java.math.BigInteger(1, digest));
    }

    // DateTime Function
    public Date getFormatsDateMilli() throws ParseException {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        Date dates = cal.getTime();
        return dates;
    }

    public static String generateDatetimeToString(Date date) throws ParseException {
        String formatDate = "yyyy-MM-dd";
        DateFormat dateFormat = new SimpleDateFormat(formatDate);

        return dateFormat.format(date);
    }

    public String generateDateTimeToThai(Date date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        return day + " " + convert2FullMonth(String.valueOf(month)) + " " + (year + 543);
    }

    public static String convert2FullMonth(String month) {
        String monthName = "";
        if (month.equals("1")) {
            monthName = "มกราคม";
        } else if (month.equals("2")) {
            monthName = "กุมภาพันธ์";
        } else if (month.equals("3")) {
            monthName = "มีนาคม";
        } else if (month.equals("4")) {
            monthName = "เมษายน";
        } else if (month.equals("5")) {
            monthName = "พฤษภาคม";
        } else if (month.equals("6")) {
            monthName = "มิถุนายน";
        } else if (month.equals("7")) {
            monthName = "กรกฎาคม";
        } else if (month.equals("8")) {
            monthName = "สิงหาคม";
        } else if (month.equals("9")) {
            monthName = "กันยายน";
        } else if (month.equals("10")) {
            monthName = "ตุลาคม";
        } else if (month.equals("11")) {
            monthName = "พฤศจิกายน";
        } else if (month.equals("12")) {
            monthName = "ธันวาคม";
        }
        return monthName;
    }

    public static String getDatetimeDbFormat(String date, String format) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat df = new SimpleDateFormat(format);
        Date d = df.parse(date);
        return dateFormat.format(d);
    }

    public static Date string2DatetimeDbFormat(String date) throws ParseException {
        if (date != null) {
            if (!date.trim().equals("")) {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date d = df.parse(date);
                return d;
            }
        }
        return null;
    }
}

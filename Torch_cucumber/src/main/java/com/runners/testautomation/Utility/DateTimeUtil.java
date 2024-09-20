package com.runners.testautomation.Utility;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.sql.Timestamp;

public class DateTimeUtil {

    public static String getTimeStamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.toString().replaceAll("[^a-zA-Z0-9]", "");
    }
    //	format 31 Jan 2024
    public static String getCurrentDate(String specificDate) {
        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Define the formatter in the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");


        // Format the current date
        String formattedCurrentDate = currentDate.format(formatter);

        // Define the specific date string to compare wit

        // Compare the formatted current date with the specific date string
        boolean isSameDate = formattedCurrentDate.equals(specificDate);

        // Output the results
        System.out.println("Current Date (Formatted): " + formattedCurrentDate);
        return formattedCurrentDate;
    }
}

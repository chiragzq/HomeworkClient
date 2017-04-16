package com.chirag.homeworkclient;

import java.sql.Date;

class DateUtil {
    private static final String[] weekdayNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};

    static final String[] monthNames = {"", "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    static String dateString(Date date) {
        Date todayDate = new Date(System.currentTimeMillis()); //get a sql date with today

        int diff = getDay(todayDate) - getDay(date);
        int dayNum;
        switch(diff) {  //check if relative phrases should be used
            case 2:
                return "2 days ago";
            case 1:
                return "Yesterday";
            case 0:
                return" Today";
            case -1:
                return "Tomorrow";
        }

        dayNum = (getDay(date) +        //calculate the day of the week the date is
                getMonthCode(date) +
                (getYear(date) % 100) +
                ((getYear(date) % 100) / 4)) % 7;

        String weekday = weekdayNames[dayNum];

        if((getDay(todayDate) - getDay(date)) < 0 && (getDay(todayDate) - getDay(date)) > -7) {  //decide whether the date is close enough to not include the full format
            return weekday;
        } else {
            String monthString = monthNames[Integer.parseInt(date.toString().substring(5,7))];
            return weekday + ", " + monthString + " " +  getDay(date);   //eg. Friday, February 17
        }
    }

    private static int getMonthCode(Date date) {    //used to calculate the day of the week the date is on
        int year = getYear(date);
        int month = Integer.parseInt(date.toString().substring(5,7));
        if(year % 100 > 0 && year % 400 > 0 && year % 4 == 0) {
            switch(month) {
                case 10: return 0;
                case 5: return 1;
                case 2: case 8: return 2;
                case 3: case 11: return 3;
                case 6: return 4;
                case 9: case 12 : return 5;
                case 1 :case 4: case 7: return 6;
            }
        } else {
            switch(month) {
                case 1: case 10: return 0;
                case 5: return 1;
                case 8: return 2;
                case 2: case 3: case 11: return 3;
                case 6: return 4;
                case 9: case 12 : return 5;
                case 4: case 7: return 6;
            }
        }
        return 0;
    }

    static int dateOffset(Date date) {    //used to calculate the day of the week the date is on
        int year = DateUtil.getYear(date);
        int month = Integer.parseInt(date.toString().substring(5,7));
        int monthCode = 0;
        String weekday;
        String[] weekdayNames = {"Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        if(year % 100 > 0 && year % 400 > 0 && year % 4 == 0) {
            switch(month) {
                case 10: monthCode = 0; break;
                case 5: monthCode = 1; break;
                case 2: case 8: monthCode = 2; break;
                case 3: case 11: monthCode = 3; break;
                case 6: monthCode = 4; break;
                case 9: case 12 : monthCode = 5; break;
                case 1 :case 4: case 7: monthCode = 6;
            }
        } else {
            switch(month) {
                case 1: case 10: monthCode = 0; break;
                case 5: monthCode = 1; break;
                case 8: monthCode = 2; break;
                case 2: case 3: case 11: monthCode = 3; break;
                case 6: monthCode = 4; break;
                case 9: case 12 : monthCode = 5; break;
                case 4: case 7: monthCode = 6;
            }
        }
        int dayCode = (DateUtil.getDay(date) + monthCode +  (DateUtil.getYear(date) % 100) + ((DateUtil.getYear(date) % 100) / 4)) % 7;
        weekday = weekdayNames[dayCode];
        switch(weekday) {
            case "Sunday":
                return 0;
            case "Monday":
                return 1;
            case "Tuesday":
                return 2;
            case "Wednesday":
                return 3;
            case "Thursday":
                return 4;
            case "Friday":
                return 5;
            case "Saturday":
                return 6;
        }
        return 0;
    }

    static int daysInMonth(Date date) {
        int [] monthDays = {0,31,0,31,30,31,30,31,31,30,31,30,31};
        int month = Integer.parseInt(date.toString().substring(5,7));
        int year = getYear(date);

        if(month != 2) {
            return monthDays[month];
        } else {
            return (year % 100 > 0 && year % 400 > 0 && year % 4 == 0) ? 29 : 28;
        }

    }
    static int getYear(Date date) {
        return Integer.parseInt(date.toString().substring(0,4));
    }

    static int getMonth(Date date) {
        return Integer.parseInt(date.toString().substring(5,7));
    }

    static int getDay(Date date) {
        return Integer.parseInt(date.toString().substring(8,10));
    }
}

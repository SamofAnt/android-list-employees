package com.example.listemployees;

import java.util.Calendar;

public class Human {
    public String firstName;
    public String lastName;
    public boolean gender;
    public Calendar birthDay;
    public	Human(String firstName, String lastName,
                    boolean gender, Calendar birthDay)
    {
        this.firstName	 = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDay = birthDay;
    }
    public String getBirthDayString()
    {
        String str = "";
        int day = this.birthDay.get(Calendar.DAY_OF_MONTH);
        str += ((day < 10)?"0":"") + day + "/";
        int mon = this.birthDay.get(Calendar.MONTH) + 1;
        str += ((mon < 10)?"0":"") + mon + "/";
        str += this.birthDay.get(Calendar.YEAR);
        return str;
    }
    public	static Calendar makeCalendar(int day,
                                           int month, int year)
    {
        Calendar C = Calendar.getInstance();
        C.setTimeInMillis(0);
        C.set(Calendar.YEAR, year);
        C.set(Calendar.MONTH, month - 1);
        C.set(Calendar.DAY_OF_MONTH, day);
        return C;
    }
}

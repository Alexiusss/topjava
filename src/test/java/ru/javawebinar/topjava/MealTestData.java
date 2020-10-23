package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final LocalDate DATE = parseLocalDate("2020-10-16");
    public static final LocalDate DATE2 = parseLocalDate("2020-10-17");

    public static final LocalDateTime DATE_TIME =  LocalDateTime.of(DATE, LocalTime.of(9,00,01));

    public static final int MEAL_ID = START_SEQ+2;

    public static final Meal MEAL = new Meal(MEAL_ID, DATE_TIME, "Завтрак", 500);


}

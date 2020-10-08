package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 9, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 15, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.SEPTEMBER, 15, 19, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 8, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 16, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.SEPTEMBER, 16, 21, 0), "Ужин", 510)
    );

   // public static Map<Integer, Meal> MEALS = new ConcurrentHashMap<>();

//   static {
//        MEALS.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
//        MEALS.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500));
//        MEALS.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
//        MEALS.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500));
//        MEALS.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
//        MEALS.put(1, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
//        MEALS.put(2, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1500));
//        MEALS.put(2, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 100));
//        MEALS.put(2, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 200));
//    }




    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return getFiltered(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return getFiltered(meals, caloriesPerDay, meal -> Util.isBetweenInclusive(meal.getTime(), startTime, endTime));
    }

    private static List<MealTo> getFiltered(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
                );

        return meals.stream()
                .filter(filter)
                .map(meal ->
                        new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(),
                                caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

    public static List<MealTo> getSortedByDateOrTime(List<MealTo> list, Comparator<MealTo> comparing) {
        return list.stream().sorted(comparing).collect(Collectors.toList());
    }
}
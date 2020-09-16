package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
        System.out.format("Hello TopJava Enterprise!");
        //MealsUtil.meals.forEach(System.out::println);
        //MealsUtil.getMealsWithExcess().forEach(System.out::println);

        List<Meal> mealTos = MealsUtil.MEALS;
        System.out.println(DateTimeUtil.toString(MealsUtil.getTos(mealTos, 2000).get(2).getDateTime()));

    }


}

package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.InMemoryMealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.format("Hello TopJava Enterprise!");


        List<Meal> mealTos = MealsUtil.MEALS;
        System.out.println(DateTimeUtil.toString(MealsUtil.getTos(mealTos, 2000).get(2).getDateTime()));
        InMemoryMealRepository repository = new InMemoryMealRepository();
        System.out.println(repository.get(0));

    }


}

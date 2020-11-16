package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;


import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser(){
        Meal actualMeal = service.getWithUser(ADMIN_MEAL_ID, ADMIN_ID);
        assertMatch(actualMeal, ADMIN_MEAL1);
        UserTestData.assertMatch(actualMeal.getUser(), UserTestData.ADMIN);

    }
}

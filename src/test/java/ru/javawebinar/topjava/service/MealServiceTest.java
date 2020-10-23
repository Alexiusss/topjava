package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.DATE;
import static ru.javawebinar.topjava.MealTestData.DATE2;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(LocalDateTime.now(), "Тестовый обед", 1800);
        Meal created = service.create(newMeal, ADMIN_ID);
        newMeal.setId(created.getId());
        assertThat(service.get(newMeal.getId(), ADMIN_ID)).isEqualTo(newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreate() throws Exception {
        service.create(new Meal(DATE_TIME, "DuplicateMeal", 2500), USER_ID);
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void delete() throws Exception {
        service.delete(MEAL_ID, USER_ID);
        service.get(MEAL_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() throws Exception {
        service.delete(111, 111);
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_ID, USER_ID);
        assertThat(meal)
                .isEqualTo(MEAL);
    }
    //@Test(expected = NotFoundException.class)
    @Test(expected = EmptyResultDataAccessException.class)
    public void getNotFound() throws Exception {
        service.get(100007, 100001);
    }

    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> all = service.getBetweenDates(DATE, DATE2, ADMIN_ID);
       assertThat(all)
               .allSatisfy(meal->assertThat(meal.getDate()).isBetween(DATE, DATE2));
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> all = service.getAll(ADMIN_ID);
        assertThat(all)
                .extracting(Meal::getId)
                .containsExactly(100009, 100008);
    }

    @Test
    public void update() throws Exception {
        Meal updated = MEAL;
        updated.setCalories(200);
        updated.setDescription("Jnvtyf");
        service.update(updated, USER_ID);
        assertThat(service.get(MEAL.getId(), USER_ID)).isEqualTo(updated);


    }


}
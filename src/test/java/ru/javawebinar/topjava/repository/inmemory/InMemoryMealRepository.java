package ru.javawebinar.topjava.repository.inmemory;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

//@Repository
//@Primary
@Component("inMemoryMealRepository")
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    // Map  userId -> mealRepository

    private Map<Integer, InMemoryBaseRepository<Meal>> usersMealsMap;// = new ConcurrentHashMap<>();

//    {
//        MealsUtil.MEALS.forEach(meal -> save(meal, USER_ID));
//
//        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510), ADMIN_ID);
//        save(new Meal(LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500), ADMIN_ID);
//    }


    @Override
    public Meal save(Meal meal, int userId) {
        InMemoryBaseRepository<Meal> meals = usersMealsMap.computeIfAbsent(userId, uid -> new InMemoryBaseRepository<>());
        return meals.save(meal);
    }

    @PostConstruct
    @Primary
    public void postConstruct() {
        log.info("+++ PostConstruct");
       // usersMealsMap.clear();

        usersMealsMap = new ConcurrentHashMap<>();

        MealsUtil.MEALS.forEach(meal -> save(meal, UserTestData.USER_ID));

        save(new Meal(LocalDateTime.of(2020, Month.OCTOBER, 1, 14, 0), "Админ ланч", 510), UserTestData.ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020, Month.OCTOBER, 2, 21, 0), "Админ ужин", 1500), UserTestData.ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020, Month.OCTOBER, 3, 21, 0), "Админ ужин", 1500), UserTestData.ADMIN_ID);

    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");

    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        return meals != null && meals.delete(id);
    }

    @Override
    public Meal get(int id, int userId) {
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        return meals == null ? null : meals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAllFiltered(userId, meal -> Util.isBetweenInclusive(meal.getDateTime(), startDateTime, endDateTime));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        InMemoryBaseRepository<Meal> meals = usersMealsMap.get(userId);
        return meals == null ? Collections.emptyList() :
                meals.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                        .collect(Collectors.toList());
    }
}
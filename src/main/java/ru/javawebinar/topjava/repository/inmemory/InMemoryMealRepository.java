package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.Util;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    private Map<Integer, Map<Integer, Meal>> usersMealsMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    //    {
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), 1);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 500), 1);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500), 1);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500), 1);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500), 1);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510), 1);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1500), 2);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 100), 2);
//        save(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 200), 2);
//    }
    {
        MealsUtil.MEALS.forEach(meal -> save(meal, USER_ID));
        save(new Meal(LocalDateTime.of(2020, Month.OCTOBER, 2, 11, 0), "Админский завтрак", 810), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020, Month.OCTOBER, 2, 17, 0), "Админский завтрак обед", 700), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020, Month.OCTOBER, 2, 22, 0), "Админский завтрак ужин", 500), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
//        Map<Integer, Meal> mealsRepo = new ConcurrentHashMap<>();
//        if (usersMealsMap.get(userId) != null) {
//            mealsRepo = usersMealsMap.get(userId);
//        }
//        if (meal.isNew()) {
//            meal.setId(counter.incrementAndGet());
//            mealsRepo.put(meal.getId(), meal);
//        }
//        mealsRepo.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
//        usersMealsMap.put(userId, mealsRepo);
//        return meal;
        log.info("save {}", meal, userId);
        Map<Integer, Meal> meals = usersMealsMap.computeIfAbsent(userId, ConcurrentHashMap::new);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
//        if (checkUserId(userId)) return false;
//        Optional<Boolean> delete = Optional.of(usersMealsMap.get(userId).remove(id) != null);
//        return delete.orElse(false);
        Map<Integer, Meal> meals = usersMealsMap.get(userId);
        return meals != null && meals.remove(id) != null;

    }

    @Override
    public Meal get(int id, int userId) {
//        if (checkUserId(userId)) return null;
//        Optional<Meal> check = Optional.ofNullable(usersMealsMap.get(userId).get(id));
//        return check.orElse(null);
        log.info("get {}", id, userId);
        Map<Integer, Meal> meals = usersMealsMap.get(userId);
        return meals == null ? null : meals.get(id);
    }


    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll");
        //return getSortedList(userId).collect(Collectors.toList());
        return getAllFiltered(userId, meal -> true);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        log.info("getBetween", startDate, endDate, userId);
        return getAllFiltered(userId,meal ->  Util.isBetweenInclusive(meal.getDateTime(), startDate, endDate));
    }

    private List<Meal> getAllFiltered(int userId, Predicate<Meal> filter) {
        log.info("getAllFiltered", userId, filter);
        Map<Integer, Meal> meals = usersMealsMap.get(userId);
        return CollectionUtils.isEmpty(meals) ? Collections.emptyList() :
                meals.values().stream()
                .filter(filter)
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }


//    @Override
//    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
//        return getSortedList(userId)
//                .filter(meal -> meal.getDateTime().isBefore(endDate) && meal.getDateTime().isAfter(startDate))
//                .collect(Collectors.toList());
//    }
//
//    private Stream<Meal> getSortedList(int userId) {
//        return usersMealsMap.get(userId).values().stream()
//                .sorted((m1, m2) -> m2.getTime()
//                        .compareTo(m1.getTime()));
//    }

//    private boolean checkUserId(int userId) {
//        return userId > usersMealsMap.size();
//    }

}


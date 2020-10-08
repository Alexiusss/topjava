package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.Util;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.Util.isBetweenInclusive;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

//    public MealRestController(MealService mealService) {
//        this.service = mealService;
//    }


    public void save(Meal meal) {
        service.save(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(authUserId(), id);
    }

    public Collection<Meal> getAll() {
        return service.getAll(authUserId());
    }

    public Collection<MealTo> getAllTos() {
        return getTos(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        if (service.get(id, authUserId()) == null) throw new NotFoundException("Запись не найдена!");
        else return service.get(id, authUserId());
    }

    public void update(Meal meal) {
        service.update(meal, authUserId());
    }

    public List<MealTo> sortedByStartTime(List<MealTo> list) {
        return getSortedByDateOrTime(list, comparing(MealTo::getTime));
    }

    public List<MealTo> sortedByEndTime(List<MealTo> list) {
        return getSortedByDateOrTime(list, comparing(MealTo::getTime).reversed());
    }

    public List<MealTo> sortedByStartDate(List<MealTo> list) {
        return getSortedByDateOrTime(list, comparing(MealTo::getDate));
    }

    public List<MealTo> sortedByEndDate(List<MealTo> list) {
        return getSortedByDateOrTime(list, comparing(MealTo::getDate).reversed());
    }


}
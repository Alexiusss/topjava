package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static java.util.Comparator.comparing;
import static ru.javawebinar.topjava.util.MealsUtil.*;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {

    private MealService service;

    @Autowired
    public MealRestController(MealService mealService) {
        this.service = mealService;
    }

    public void create(Meal meal) {
        //checkNew(meal);
        service.create(meal, authUserId());
    }

    public void delete(int id) {
        service.delete(id, authUserId());
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

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(meal, id);
        //log.info("update {} for user {}", meal, userId);
        service.update(meal, userId);
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

//    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
//                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
//                int userId = SecurityUtil.authUserId();
//                log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
//
//                        List<Meal> mealsDateFiltered = service.getBetweenDates(startDate, endDate, userId);
//                return MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
//            }


}
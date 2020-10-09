package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.util.MealsUtil.*;

@Service
public class MealService {

    private final MealRepository repository;
@Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int userId){
       return repository.save(meal, userId);
    }

    public void delete(int id, int userId){
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, int userId){
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<Meal> getAll(int userId){
        return repository.getAll(userId);
    }

    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId){
        return repository.getBetween(startDate, endDate, userId);
    }

    public void update(Meal meal, int userId){
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    public List<Meal> getBetweenDates(@Nullable LocalDate startDate, @Nullable LocalDate endDate, int userId) {
                return repository.getBetween(
                                DateTimeUtil.createDateTime(startDate, LocalDate.MIN, LocalTime.MIN),
                                DateTimeUtil.createDateTime(endDate, LocalDate.MAX, LocalTime.MAX), userId);
            }

}
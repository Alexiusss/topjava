package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/spring-db.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println();
            System.out.println("===========================================================================================");
            MealRestController mealController = appCtx.getBean(MealRestController.class);
            //mealController.delete(3);
            mealController.getAll().forEach(System.out::println);
//            List<MealTo> filteredMealsWithExcess =
//                    mealController.getBetween(
//                            LocalDate.of(2015, Month.MAY, 5), LocalTime.of(7, 0),
//                            LocalDate.of(2022, Month.JUNE, 5), LocalTime.of(11, 0));
//            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println("===========================================================================================");
            MealService mealService = appCtx.getBean(MealService.class);
            mealService.getAll(100000).forEach(System.out::println);
            //mealService.delete(5, 100000);
            System.out.println("===========================================================================================");
            InMemoryMealRepository repository = appCtx.getBean(InMemoryMealRepository.class);
            //repository.delete(8,100001);
            repository.getAll(100001).forEach(System.out::println);

            System.out.println("===========================================================================================");
            System.out.println("===========================================================================================");
            System.out.println("===========================================================================================");
            System.out.println("===========================================================================================");
            System.out.println("===========================================================================================");
                        System.out.println("===========================================================================================");
            JdbcMealRepository repositoryDB = appCtx.getBean(JdbcMealRepository.class);
            //repository.delete(8,100001);
            repositoryDB.getAll(100000).forEach(System.out::println);
        }
    }
}

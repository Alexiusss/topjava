package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class SpringMain {
    public static void main(String[] args) throws Exception {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("------------------------------------------------------------------------------------------------------");
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));

            System.out.println("=========================================================================================================");
            System.out.println("=========================================================================================================");

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            mealRestController.save(new Meal(LocalDateTime.of(2020, Month.OCTOBER, 7, 11, 0), "Тестовый хавчик", 18810));
            mealRestController.sortedByStartTime(new ArrayList<>(mealRestController.getAllTos())).forEach(System.out::println);
            mealRestController.getAll();



        }
    }
}

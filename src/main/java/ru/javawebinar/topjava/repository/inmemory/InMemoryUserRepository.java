package ru.javawebinar.topjava.repository.inmemory;

import com.sun.istack.internal.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, User> usersMap = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    public static final int USER_ID = 1;
    public static final int ADMIN_ID = 2;

    {
        try {
            save(new User(null, "userName5", "email@mail.ru1", "password1", Role.ROLE_ADMIN));
            save(new User(null, "userName3", "email@mail.ru2", "password2", Role.ROLE_USER));
            save(new User(null, "userName1", "email@mail.ru3", "password3", Role.ROLE_ADMIN));
            save(new User(null, "userName2", "email@mail.ru4", "password4", Role.ROLE_USER));
            save(new User(null, "userName4", "email@mail.ru5", "password5", Role.ROLE_ADMIN));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return usersMap.remove(id) != null;
    }

    @Override
    public User save(User user) throws Exception {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            usersMap.put(user.getId(), user);
            return user;
        }
        return usersMap.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return usersMap.get(id);
    }

    @Override
    @NotNull
    public List<User> getAll() {
        log.info("getAll");
        return usersMap.values().stream()
                .sorted(Comparator.comparing(User::getName).thenComparing(User::getEmail))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        final User[] user1 = new User[1];
        usersMap.values().forEach(user -> {
            if (user.getEmail().equals(email)) {
                user1[0] = user;
            }
        });
        return user1[0];
    }

}

package ru.javawebinar.topjava.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminUIController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    public ResponseEntity<String> createOrUpdate(@Valid UserTo userTo, BindingResult result) {
        if (result.hasErrors()) {
            StringJoiner joiner = new StringJoiner("<br>");
            result.getFieldErrors().forEach(
                    fe -> {
                        String msg = fe.getDefaultMessage();
                        if (msg != null) {
                            if (!msg.startsWith(fe.getField())) {
                                msg = fe.getField() + ' ' + msg;
                            }
                            joiner.add(msg);
                        }
                    });
            return ResponseEntity.unprocessableEntity().body(joiner.toString());
        }
        if (userTo.isNew()) {
            super.create(userTo);
        } else {
            super.update(userTo, userTo.id());
        }
    }

    @Override
    @PostMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        super.enable(id, enabled);
    }
}
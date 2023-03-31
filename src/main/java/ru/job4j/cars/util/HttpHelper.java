package ru.job4j.cars.util;

import lombok.experimental.UtilityClass;
import org.springframework.ui.Model;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;

@UtilityClass
public class HttpHelper {

    public void addUserToModel(Model model, HttpSession httpSession) {
        User user = (User) httpSession.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
            httpSession.setAttribute("user", user);
        }
        model.addAttribute("user", user);
    }
}

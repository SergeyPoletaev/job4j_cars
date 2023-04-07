package ru.job4j.cars.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Controller
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/login")
    public String loginForm(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        return "/user/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpSession httpSession) {
        Optional<User> userDb = userService.findByLogin(user.getLogin());
        if (userDb.isPresent()
                && user.getPassword().equals(userDb.get().getPassword())) {
            httpSession.setAttribute("user", userDb.get());
            return "redirect:/post/posts";
        }
        return "redirect:/user/login?fail=true";
    }

    @GetMapping("/add")
    public String add(Model model) {
        List<TimeZone> zones = Arrays.stream(TimeZone.getAvailableIDs())
                .map(TimeZone::getTimeZone)
                .toList();
        model.addAttribute("zones", zones);
        return "/user/add";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute User user, RedirectAttributes redirectAttributes, Model model) {
        Optional<User> userOpt = userService.create(user);
        if (userOpt.isPresent() && userOpt.get().getId() != 0) {
            return "redirect:/user/login";
        }
        if (userOpt.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Пользователь с таким логином уже существует!");
            return "redirect:/user/add";
        }
        model.addAttribute("error_message",
                "При регистрации пользователя возникла ошибка, пользователь не зарегистрирован");
        return "redirect:/error/fail";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/user/login";
    }
}

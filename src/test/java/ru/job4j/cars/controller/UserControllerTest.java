package ru.job4j.cars.controller;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.job4j.cars.model.User;
import ru.job4j.cars.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void whenGetLoginFormWithFailFalse() {
        Model model = mock(Model.class);
        UserController userController = new UserController(mock(UserService.class));
        String page = userController.getLoginForm(model, false);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("/user/login");
    }

    @Test
    void whenGetLoginFormWithFailTrue() {
        Model model = mock(Model.class);
        UserController userController = new UserController(mock(UserService.class));
        String page = userController.getLoginForm(model, true);
        verify(model).addAttribute("fail", true);
        assertThat(page).isEqualTo("/user/login");
    }

    @Test
    void whenGetLoginFormWithFailNull() {
        Model model = mock(Model.class);
        UserController userController = new UserController(mock(UserService.class));
        String page = userController.getLoginForm(model, null);
        verify(model).addAttribute("fail", false);
        assertThat(page).isEqualTo("/user/login");
    }

    @Test
    void whenCredIsCorrectThenLoginSuccess() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User();
        user.setLogin("login");
        user.setPassword("pass");
        when(userService.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        HttpSession httpSession = mock(HttpSession.class);
        String page = userController.login(user, httpSession);
        verify(httpSession).setAttribute("user", user);
        assertThat(page).isEqualTo("redirect:/post/posts");
    }

    @Test
    void whenCredNotCorrectThenLoginFail() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User();
        user.setLogin("login");
        user.setPassword("pass");
        when(userService.findByLogin(user.getLogin())).thenReturn(Optional.empty());
        HttpSession httpSession = mock(HttpSession.class);
        String page = userController.login(user, httpSession);
        assertThat(page).isEqualTo("redirect:/user/login?fail=true");
    }

    @Test
    void getRegistrationForm() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        Model model = mock(Model.class);
        String page = userController.getRegistrationForm(model);
        List<TimeZone> zones = Arrays.stream(TimeZone.getAvailableIDs())
                .map(TimeZone::getTimeZone)
                .toList();
        verify(model).addAttribute("zones", zones);
        assertThat(page).isEqualTo("/user/add");
    }

    @Test
    void whenRegistrationSuccess() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User();
        when(userService.findByLogin(user.getLogin())).thenReturn(Optional.empty());
        User userDb = new User();
        userDb.setId(1);
        when(userService.create(user)).thenReturn(userDb);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        Model model = mock(Model.class);
        String page = userController.registration(user, attr, model);
        assertThat(page).isEqualTo("redirect:/user/login");
    }

    @Test
    void whenRegistrationFail() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User();
        when(userService.findByLogin(user.getLogin())).thenReturn(Optional.empty());
        when(userService.create(user)).thenReturn(user);
        RedirectAttributes attr = mock(RedirectAttributes.class);
        Model model = mock(Model.class);
        String page = userController.registration(user, attr, model);
        verify(model).addAttribute("message",
                "При регистрации пользователя возникла ошибка, пользователь не зарегистрирован");
        assertThat(page).isEqualTo("/shared/fail");
    }

    @Test
    void whenLoginAlreadyExistsRegistrationFail() {
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        User user = new User();
        when(userService.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        RedirectAttributes attr = mock(RedirectAttributes.class);
        Model model = mock(Model.class);
        String page = userController.registration(user, attr, model);
        verify(attr).addFlashAttribute("message", "Пользователь с таким логином уже существует!");
        assertThat(page).isEqualTo("redirect:/user/add");
    }

    @Test
    void logout() {
        UserController userController = new UserController(mock(UserService.class));
        HttpSession httpSession = mock(HttpSession.class);
        String page = userController.logout(httpSession);
        verify(httpSession).invalidate();
        assertThat(page).isEqualTo("redirect:/user/login");
    }
}
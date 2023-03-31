package ru.job4j.cars.util;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import ru.job4j.cars.model.User;

import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.*;

class HttpHelperTest {

    @Test
    void whenAddNotNullUserToModel() {
        User user = new User();
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        when((User) httpSession.getAttribute("user")).thenReturn(user);
        HttpHelper.addUserToModel(model, httpSession);
        verify(model).addAttribute("user", user);
    }

    @Test
    void whenAddNullUserToModel() {
        User user = new User();
        Model model = mock(Model.class);
        HttpSession httpSession = mock(HttpSession.class);
        when((User) httpSession.getAttribute("user")).thenReturn(null);
        HttpHelper.addUserToModel(model, httpSession);
        verify(httpSession).setAttribute("user", user);
        verify(model).addAttribute("user", user);
    }
}